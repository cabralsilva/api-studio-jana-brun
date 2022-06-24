<template>
  <v-form ref="addItemForm" lazy-validation>
    <v-row class="elevation-6 my-2">
      <v-col>
        <v-row>
          <v-col class="col-3">
            <v-text-field class="pointer"
              :value="itemEdit.product.description"
              filled
              clear-icon="mdi-close-circle"
              clearable
              label="Produto"
              readonly
              @click="showProductDialog()"
              @click:prepend-inner="showProductDialog()"
              @click:clear="clearItemEdit ()"
            ></v-text-field>
          </v-col>
          <component v-for="(grate, index) in itemEdit.product.grateList" :key="index" :is="'app-select'"
          :label="grate.description"
          :callback="(select) => selectGrateItem(select)"
          :params="grate.identifier"
          :asyncLoading="getGrateItemList"/>
          <v-col>
            <v-text-field v-model="itemEdit.quantity" label="Quantidade"/>
          </v-col>
        </v-row>
        <v-row align="end">
          <v-col align="right">
            <template v-if="itemEdit.unitPrice">
              <span class="pr-4" style="font-size: 12px">{{ `R$ ${itemEdit.unitPrice} x ${itemEdit.quantity} ` }}</span>
              <span class="pr-4 subheading">Total: <b style="font-size: 18px">{{ `R$ ${itemEdit.totalPrice}` }}</b></span>
            </template>
            <v-btn :disabled="!enabledToAdd()" color="primary" @click="addItem"> Add <v-icon>mdi-cart-arrow-down</v-icon></v-btn>
          </v-col>
        </v-row>
        <v-row justify="center">
          <product-dialog
            :key="productDialogKey"
            :onSelect="onSelectProduct"
            :pShow="productDialog"
            :pData="searchProducts"
            :pHeaders="headersDialog"
            />
        </v-row>
      </v-col>
    </v-row>
  </v-form>
</template>

<script lang="ts">
import Vue from 'vue'
import { HttpSearchRequest } from '@/model/HttpUtils'
import ProductDialog from '@/components/SelectDialog.vue'
import httpAPI from '@/plugins/axios'
import AppSelect from '@/components/AppSelect.vue'
import EventBus from '@/store/enventBus'

export default Vue.extend({
  name: 'RowAddProduct',
  components: {
    ProductDialog,
    AppSelect
  },
  props: {
    onAdd: {
      type: Function,
      required: true
    }
  },
  data () {
    return {
      productDialog: false,
      productDialogKey: 0,
      headersDialog: [
        {
          text: 'ID',
          align: 'start',
          sortable: false,
          value: 'identifier'
        },
        { text: 'Descrição', value: 'description' }
      ],
      itemEdit: {
        product: {
          identifier: null as any,
          description: null as any,
          grateList: [] as any
        },
        grateItemList: [] as any,
        quantity: 1,
        unitPrice: null as any,
        totalPrice: null as any,
        additionPercent: null as any
      },
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      }
    }
  },
  methods: {
    async getPriceTotalItem () {
      if (this.itemEdit.product.identifier) {
        const response = await httpAPI.post('/price-table-item/get-by-cart-item', this.itemEdit)
          .then((res: any) => {
            this.itemEdit.unitPrice = res.data.data.price
            this.itemEdit.totalPrice = this.itemEdit.unitPrice * this.itemEdit.quantity
          })
          .catch(() => {
            this.itemEdit.unitPrice = undefined
            this.itemEdit.totalPrice = undefined
          })
      }
    },
    addItem () {
      if (!(this.$refs.addItemForm as Vue & { validate: () => boolean }).validate()) {
        EventBus.invalidForm('Preencha todos os campos obrigatórios')
        return
      }

      this.onAdd(this.itemEdit)
      this.clearItemEdit()
      // this.proccessFinancial()
    },
    clearItemEdit () {
      this.itemEdit = {
        product: {
          identifier: null as any,
          description: null as any,
          grateList: [] as any
        },
        unitPrice: null as any,
        quantity: 1 as number,
        totalPrice: null as any,
        additionPercent: null as any,
        grateItemList: [] as any
      }
    },
    onSelectProduct (select: any) {
      this.getProduct(select[0].identifier)
    },
    enabledToAdd () {
      if (this.itemEdit.grateItemList.length !== this.itemEdit.product.grateList.length) {
        return false
      }

      if (!this.itemEdit.unitPrice) {
        return false
      }

      if (!this.itemEdit.totalPrice) {
        return false
      }

      return true
    },
    selectGrateItem (grateItem: any) {
      for (const grateItemAux of this.itemEdit.grateItemList) {
        if (grateItemAux.grate.identifier === grateItem.grate.identifier) {
          this.itemEdit.grateItemList.splice(this.itemEdit.grateItemList.indexOf(grateItemAux), 1)
        }
      }
      this.itemEdit.grateItemList.push(grateItem)
    },
    async getGrateItemList (grateId: any) {
      const search = {
        example: {
          grate: {
            identifier: grateId
          }
        },
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/grate-item/search', search)
      return response.data.data.result
    },
    async getProduct (identifier: any) {
      const search = {
        resultUnique: true,
        example: {
          identifier: identifier
        }
      } as HttpSearchRequest
      const response = await httpAPI.post('/product/search', search)
      this.itemEdit.product = response.data.data.result[0]
    },
    async searchProducts (search: any) {
      search.customSearch = true
      var payload = {} as any
      await httpAPI.post('/product/search', search)
        .then((response) => {
          payload.itemList = Object.keys(response.data.data.result).map(index => {
            const item = {
              identifier: response.data.data.result[index].identifier,
              description: response.data.data.result[index].description
            }
            return item
          })
          payload.total = response.data.data.total
          payload.loading = false
        })
      return payload
    },
    showProductDialog () {
      this.clearItemEdit()
      this.productDialog = true
      this.productDialogKey++
    }
  },
  watch: {
    itemEdit: {
      handler (newValue, oldValue) {
        this.getPriceTotalItem()
      },
      deep: true
    }
  }
})
</script>
<style scoped>
.pointer :hover {
  cursor: pointer;
}
</style>
