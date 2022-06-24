<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formProduct" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="product.code" :rules="[required]" label="Código"/>
            </v-col>
            <v-col>
              <v-text-field v-model="product.description" :rules="[required]" label="Descrição"/>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field :rules="[required, number]" v-model="product.unitPrice" label="Preço unitário" />
            </v-col>
            <v-col>
              <v-select v-model="product.status" :rules="[requiredSelect]" :items="statusActiveList" item-text="value" item-value="key" label="Status" />
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-select v-model="product.category" :rules="[requiredSelect]" :items="productCategoryList" item-text="value" item-value="key" label="Categoria" />
            </v-col>
            <v-col>
              <v-select v-model="product.grateList" :items="this.grateList"
              item-text="description" item-value="identitifer"
              attach chips label="Grades" multiple return-object/>
            </v-col>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/register/product/product')"> Cancelar </v-btn>
              <v-btn color="primary"  @click="save"> Salvar </v-btn>
            </v-col>
          </v-row>
        </v-form>
      </v-card>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'
import { StatusActiveList } from '@/model/StatusActiveEnum'
import { ProductCategoryList } from '@/model/ProductCategoryEnum'

export default Vue.extend({
  components: {
  },
  data () {
    return {
      productCategoryList: ProductCategoryList,
      statusActiveList: StatusActiveList,
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      product: {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        status: null,
        category: null as any,
        unitPrice: 0.0
      },
      grateList: []
    }
  },
  watch: {
  },
  methods: {
    required (value: any) {
      return !!value || 'Required.'
    },
    onlyNumber (value: any) {
      if (value && value.length > 0) {
        const lastChar = value.charAt(value.length - 1)
        if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
          return 'Invalid format'
        }
      }
      return true
    },
    requiredSelect (value: any) {
      if (value) {
        if (value.identifier) {
          return true
        }

        if (value.length) {
          return true
        }
      }
      return 'Required.'
    },
    number (value: any) {
      if (value && value.length > 0) {
        const lastChar = value.charAt(value.length - 1)
        if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
          this.product.unitPrice = value.slice(0, -1)
        }
        const valueSplit = value.split('.')
        if (valueSplit.length > 2) {
          valueSplit.splice(2)
          this.product.unitPrice = valueSplit.join('.')
          value = this.product.unitPrice
        }

        if (valueSplit[1] && valueSplit[1].length > 2) {
          valueSplit[1] = valueSplit[1].substring(0, 2)
          this.product.unitPrice = valueSplit.join('.')
          value = this.product.unitPrice
        }

        if (!value.match(/^\d{0,5}(?:\.\d{1,2})?$/)) {
          return 'Invalid format'
        }
      }
      return true
    },
    navigation (to: any) {
      // eslint-disable-next-line
      this.$router.push(to).catch(_err => { })
    },
    async save () {
      if ((this.$refs.formProduct as Vue & { validate: () => boolean }).validate()) {
        console.log('passou: ', this.product)
        try {
          if (this.product.identifier) {
            await httpAPI.put('/product', this.product)
          } else {
            await httpAPI.post('/product', this.product)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          this.navigation('/panel/register/product/product')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    clear () {
      this.product = {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        status: null,
        category: null as any,
        unitPrice: 0.0
      }
    },
    async getProduct () {
      if (this.product.identifier) {
        const search = {
          columnList: [
            'identifier',
            'code',
            'description',
            'status',
            'unitPrice'
          ],
          resultUnique: true,
          example: {
            identifier: this.product.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/product/search', search)
        this.product = response.data.data.result[0]
      }
    },
    async getGrateList () {
      const search = {
        columnList: [
          'identifier',
          'code',
          'description'
        ],
        example: {
          status: 'ACTIVE'
        }
      } as HttpSearchRequest
      const response = await httpAPI.post('/grate/search', search)
      this.grateList = response.data.data.result
    }
  },
  created () {
    this.getGrateList()
    if (this.product.identifier) {
      this.getProduct()
    }
  }
})
</script>
