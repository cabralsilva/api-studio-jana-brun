<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formPriceTable" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="priceTable.code" :rules="[required]" label="Código"/>
            </v-col>
            <v-col>
              <v-text-field v-model="priceTable.description" :rules="[required]" label="Descrição"/>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <input-date startIn="DATE" :callback="(date) => priceTable.initDate = date" :initDate="priceTable.initDate" label="Data de início" />
            </v-col>
            <v-col>
              <input-time :callback="(time) => priceTable.initTime = time" :initTime="priceTable.initTime" label="Hora de início" />
            </v-col>
            <v-col>
              <input-date startIn="DATE" :callback="(date) => priceTable.endDate = date" :initDate="priceTable.endDate" label="Data de término" />
            </v-col>
            <v-col>
              <input-time :callback="(time) => priceTable.endTime = time" :initTime="priceTable.endTime" label="Hora de término" />
            </v-col>
          </v-row>
          <v-row class="elevation-6 ma-0">
            <v-col cols="3">
              <v-select v-model="itemEdit.product" :items="this.productList"
                item-text="description" item-value="identitifer"
                attach chips label="Selecione o produto" return-object
                :rules="[requiredSelect]"
                @change="selectProduct(itemEdit.product)"/>
            </v-col>
            <component v-for="(grate, index) in itemEdit.product.grateList" :key="index" :is="'app-select-multiple'"
              :name="grate.identifier"
              :label="grate.description"
              :params="grate.identifier"
              :asyncLoading="getGrateItemList"
              :callback="(select, grateIdentifier) => changeGrateItemList(select, grate.identifier)"/>
            <v-col v-if="itemEdit.product.identifier" align="right">
              <v-text-field v-model="itemEdit.price" :rules="[required, number]" label="Preço"/>
            </v-col>
            <v-col align="right">
              <v-btn color="secondary"  @click="addItem"> Add Item</v-btn>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left">
                        Produto
                      </th>
                      <th class="text-center">
                        Grades
                      </th>
                      <th class="text-left">
                        Preço
                      </th>
                      <th class="text-left">
                        Ação
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(item, index) in priceTable.itemList.slice().reverse()" :key="item.identifier">
                      <td><b>{{ item.product.description }}</b></td>
                      <td>
                        <v-row>
                          <template v-for="(grate, name) of item.grateItemListGroupedByGrate">
                            <v-col :key="name" >
                              <v-card class="col-2 pa-0" outlined  flat color="transparent">
                                <v-card-text class="pa-0">
                                  <span class="subheading">{{ name }}</span>
                                  <v-chip-group active-class="deep-purple--text text--accent-4">
                                    <v-chip v-for="grateItem of grate" :key="grateItem.identifier" class="ma-2">
                                      {{ `${grateItem.value}`}}
                                    </v-chip>
                                  </v-chip-group>
                                </v-card-text>
                              </v-card>
                            </v-col>
                          </template>
                        </v-row>
                      </td>
                      <td>{{ item.price }}</td>
                      <td><v-icon @click="removeItem(index)">mdi-delete-forever</v-icon></td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </v-col>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/register/product/price-table')"> Cancelar </v-btn>
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
import { TypeOfValueList } from '@/model/TypeOfValueEnum'
import InputDate from '@/components/InputDate.vue'
import InputTime from '@/components/InputTime.vue'
import AppSelectMultiple from '@/components/AppSelectMultiple.vue'

import moment from 'moment'

export default Vue.extend({
  components: {
    InputDate,
    InputTime,
    AppSelectMultiple
  },
  data () {
    return {
      selectTimePicker: false,
      statusActiveList: StatusActiveList,
      TypeOfValueList: TypeOfValueList,
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      priceTable: {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        initDateTime: null as any,
        initDate: null as any,
        initTime: null as any,
        endDateTime: null as any,
        endDate: null as any,
        endTime: null as any,
        itemList: [] as any
      },
      productList: [],
      itemEdit: {
        product: {},
        price: null as any,
        grateItemList: [] as any,
        grateItemListGroupedByGrate: [] as any
      }
    }
  },
  watch: {
  },
  methods: {
    removeItem (index: any) {
      const indexAux = (this.priceTable.itemList.length - 1) - index
      this.priceTable.itemList.splice(indexAux, 1)
    },
    groupGrate (item: any) {
      return item.grateItemList
        .reduce((hash: any, obj: any) => {
          if (obj.grate.description === undefined) return hash
          return Object.assign(hash, { [obj.grate.description]: (hash[obj.grate.description] || []).concat(obj) })
        }, {})
    },
    clearItemEdit () {
      return {
        product: {},
        price: null as any,
        grateItemList: [] as any,
        grateItemListGroupedByGrate: [] as any
      }
    },
    addItem () {
      if (!(this.$refs.formPriceTable as Vue & { validate: () => boolean }).validate()) {
        this.snackbar.show = true
        this.snackbar.text = 'Preencha todos os campos obrigatórios'
        this.snackbar.timeout = 0
        return
      }

      this.itemEdit.grateItemListGroupedByGrate = this.groupGrate(this.itemEdit)
      this.priceTable.itemList.push(this.itemEdit)
      this.itemEdit = this.clearItemEdit()
    },
    changeGrateItemList (grateItemList: [], grateIdentifier: number) {
      this.itemEdit.grateItemList = this.itemEdit.grateItemList.filter((grateItem: any) => grateItem.grate.identifier !== grateIdentifier)
      this.itemEdit.grateItemList = this.itemEdit.grateItemList.concat(grateItemList)
    },
    setItems (items: any) {
      this.priceTable.itemList = items
    },
    required (value: any) {
      return !!value || 'Required.'
    },
    number (value: any) {
      if (value && value.length > 0) {
        const lastChar = value.charAt(value.length - 1)
        if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
          this.itemEdit.price = value.slice(0, -1)
        }
        const valueSplit = value.split('.')
        if (valueSplit.length > 2) {
          valueSplit.splice(2)
          this.itemEdit.price = valueSplit.join('.')
          value = this.itemEdit.price
        }

        if (valueSplit[1] && valueSplit[1].length > 2) {
          valueSplit[1] = valueSplit[1].substring(0, 2)
          this.itemEdit.price = valueSplit.join('.')
          value = this.itemEdit.price
        }

        if (!value.match(/^\d{0,5}(?:\.\d{1,2})?$/)) {
          return 'Invalid format'
        }
      }
      return true
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
    navigation (to: any) {
      // eslint-disable-next-line
      this.$router.push(to).catch(_err => { })
    },
    async save () {
      if ((this.$refs.formPriceTable as Vue & { validate: () => boolean }).validate()) {
        this.priceTable.initDateTime = this.priceTable.initDate + 'T' + this.priceTable.initTime
        this.priceTable.endDateTime = this.priceTable.endDate + 'T' + this.priceTable.endTime
        console.log('passou: ', this.priceTable)
        try {
          if (this.priceTable.identifier) {
            await httpAPI.put('/price-table', this.priceTable)
          } else {
            await httpAPI.post('/price-table', this.priceTable)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          this.navigation('/panel/register/product/price-table')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    clear () {
      this.priceTable = {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        initDateTime: null as any,
        initDate: null as any,
        initTime: null as any,
        endDateTime: null as any,
        endDate: null as any,
        endTime: null as any,
        itemList: []
      }
    },
    async getPriceTable () {
      if (this.priceTable.identifier) {
        const search = {
          resultUnique: true,
          example: {
            identifier: this.priceTable.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/price-table/search', search)
        this.priceTable = response.data.data.result[0]
        this.priceTable.initDate = moment(this.priceTable.initDateTime).format('YYYY-MM-DD')
        this.priceTable.initTime = moment(this.priceTable.initDateTime).format('HH:mm')
        this.priceTable.endDate = moment(this.priceTable.endDateTime).format('YYYY-MM-DD')
        this.priceTable.endTime = moment(this.priceTable.endDateTime).format('HH:mm')
        this.priceTable.itemList.forEach((item: any) => {
          item.grateItemList.forEach((grateItem: any) => {
            item.grateItemListGroupedByGrate = this.groupGrate(item)
          })
        })
      }
    },
    async getProducts () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/product/search', search)
      this.productList = response.data.data.result
    },
    async selectProduct (product: any) {
      this.itemEdit.product = {}
      setTimeout(() => { this.itemEdit.product = product }, 10)
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
    }
  },
  created () {
    if (this.priceTable.identifier) {
      this.getPriceTable()
    }
    this.getProducts()
  }
})
</script>

<style>
.v-input__slot{
  height: 42px;
}
</style>
