<template>
  <div>
    <v-card>
      <v-card-text>
        <v-row>
          <h3 class="pa-4">Vendas</h3>
        </v-row>
        <v-row class="ma-0">
          <v-col>
            <v-card style="border: none !important;" outlined>
              <v-container fluid>
                <v-row class="elevation-6 my-2">
                  <v-col>
                    <v-text-field class="pointer"
                      :value="cart.customer.name"
                      :prepend-inner-icon="'mdi-account'"
                      dense
                      clear-icon="mdi-close-circle"
                      clearable
                      label="Cliente"
                      readonly
                      @click="showCustomerDialog()"
                      @click:prepend-inner="showCustomerDialog()"
                    />
                  </v-col>
                </v-row>
                <row-add-product :onAdd="addProduct"/>
                <v-row class="my-2">
                  <v-col cols="8">
                    <v-simple-table>
                      <template v-slot:default>
                        <thead>
                          <tr>
                            <th class="text-left col-4">
                              Item
                            </th>
                            <th class="text-left">
                              Preço Unit.
                            </th>
                            <th class="text-left">
                              Quantidade
                            </th>
                            <th class="text-left">
                              Total
                            </th>
                            <th class="text-left">
                              Ação
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr v-for="(item, index) in cart.orderItemList" :key="item.identifier">
                            <td>
                              <b>{{ `${item.product.description}` }}</b>{{ `${getSpecification(item.grateItemList)} ` }}
                            </td>
                            <td>{{ utilFinancial.getCurrencyFormat(item.unitPrice) }}</td>
                            <td>{{ `x ${item.quantity}` }}</td>
                            <td>{{ utilFinancial.getCurrencyFormat(item.totalPrice) }}</td>
                            <td><v-icon v-if="!isShowFinish()" @click="removeOrderItem(index)">mdi-delete-forever</v-icon></td>
                          </tr>
                        </tbody>
                      </template>
                    </v-simple-table>
                  </v-col>
                  <v-col class="elevation-6" cols="4">
                    <v-row class="py-2">
                      <v-col class="py-0" cols="12" align="center">
                        Total<br/>
                        <h2> {{ utilFinancial.getCurrencyFormat(cart.financial.totalPrice) }}</h2>
                      </v-col>
                      <v-col class="py-0" cols="12" align="center">
                        <small>Aberto: {{ utilFinancial.getCurrencyFormat(cart.financial.openValue) }}</small>
                      </v-col>
                    </v-row>
                    <v-divider></v-divider>
                    <v-row class="py-2">
                      <v-col>
                        <v-simple-table>
                          <template v-slot:default>
                            <thead>
                              <tr>
                                <th class="text-left col-4">
                                  Desc.
                                </th>
                                <th class="text-left">
                                  Valor
                                </th>
                                <th class="text-left">
                                  Condição
                                </th>
                                <th class="text-left">
                                  Ação
                                </th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr v-for="(billToReceive, index) in cart.billToReceiveList" :key="billToReceive.identifier">
                                <td>{{ billToReceive.title }}</td>
                                <td>{{ utilFinancial.getCurrencyFormat(billToReceive.value) }}</td>
                                <td>{{ billToReceive.paymentCondition.quantityInstallments }}x</td>
                                <td><v-icon @click="removeBillToReceiveItem(index)">mdi-delete-forever</v-icon></td>
                              </tr>
                            </tbody>
                          </template>
                        </v-simple-table>
                      </v-col>
                    </v-row>
                    <v-row class="py-2">
                      <v-col align="center">
                        <v-btn :disabled="isDisabledAddPayment()" color="primary" @click="showPaymentDialog()">
                          <v-icon>mdi-cash-plus</v-icon>
                          Add Pagamento
                        </v-btn>
                      </v-col>
                      <v-col v-if="isShowFinish()" align="center">
                        <v-btn color="success" :loading="btnFinish.loading" @click="save()">
                          <v-icon>mdi-cash-register</v-icon>
                          {{btnFinish.text}}
                        </v-btn>
                      </v-col>
                    </v-row>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-col>
        </v-row>
        <v-row justify="center">
          <customer-dialog
            :key="customerDialogKey"
            :onSelect="onSelectCustomer"
            :pShow="customerDialog"
            :pData="searchCustomers"/>
        </v-row>
        <v-row justify="center">
          <payment-single-dialog
            :key="paymentDialogKey"
            :callback="addPayment"
            :pShow="paymentDialog"
            :pModel="enrichBillToReceiveModel()"/>
        </v-row>
        <v-row justify="center">
          <alert-dialog
            :key="alertDialogKey"
            :callback="navigation.reloadPage"
            :pShow="alertDialog"
            :pType="alert.type"
            :pTitle="alert.title"
            :pContent="alert.content"/>
        </v-row>
      </v-card-text>
    </v-card>
    <app-snackbar/>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'
import CustomerDialog from '@/components/SelectDialog.vue'
import PaymentSingleDialog from '@/components/PaymentSingleDialog.vue'
import AlertDialog from '@/components/AlertDialog.vue'
import RowAddProduct from '@/components/RowAddProduct.vue'
import { TypeOfBillToReceiveEnum } from '@/model/TypeOfBillToReceiveEnum'
import { StatusOfBillEnum } from '@/model/StatusOfBillEnum'
import moment from 'moment'
import { Financial, Navigation } from '@/lib/Utils'
import AppSnackbar from '@/components/AppSnackbar.vue'

export default Vue.extend({
  components: {
    CustomerDialog,
    PaymentSingleDialog,
    RowAddProduct,
    AlertDialog,
    AppSnackbar
  },
  data () {
    return {
      btnFinish: {
        loading: false,
        text: 'Finalizar'
      },
      alert: {
        title: 'Sucesso',
        content: '',
        type: 'success'
      },
      customerDialog: false,
      customerDialogKey: 1,
      paymentDialog: false,
      paymentDialogKey: 1,
      alertDialog: false,
      alertDialogKey: 1,
      cart: {
        identifier: null as any,
        customer: {
          identifier: null as any,
          name: null as any
        },
        orderItemList: [] as any,
        additionalValue: null as any,
        isBudget: null as any,
        billToReceiveList: [] as any,
        financial: {
          totalPrice: 0 as number,
          openValue: 0 as number,
          paidValue: 0 as number,
          additionValue: 0 as number
        }
      },
      productList: [] as any,
      utilFinancial: new Financial(),
      navigation: new Navigation()
    }
  },
  watch: {
    'cart.orderItemList': {
      handler () {
        this.proccessFinancial()
      },
      deep: true
    },
    'cart.billToReceiveList': {
      handler () {
        this.proccessFinancial()
      },
      deep: true
    }
  },
  computed: {

  },
  methods: {
    async save () {
      this.btnFinish.loading = true
      try {
        const response = await httpAPI.post('/order', this.cart)
        this.cart.identifier = response.data.data.identifier
        this.alert.title = 'Pedido finalizado'
        this.alert.content = 'Código do pedido: <b>' + this.cart.identifier + '</b>'
        this.alert.type = 'success'
        this.showAlertDialog()
      } catch (error) {
        console.log(error)
      }
      this.btnFinish.loading = false
    },
    removeOrderItem (index: any) {
      this.cart.orderItemList.splice(index, 1)
    },
    removeBillToReceiveItem (index: any) {
      this.cart.billToReceiveList.splice(index, 1)
    },
    isDisabledAddPayment () {
      if (!this.cart?.customer?.identifier) {
        return true
      }

      if (this.cart?.financial?.openValue <= 0) {
        return true
      }
      return false
    },
    isShowFinish () {
      if (!this.cart?.customer?.identifier) {
        return false
      }

      if (this.cart?.orderItemList?.length === 0) {
        return false
      }

      if (this.cart?.financial?.openValue > 0) {
        return false
      }
      return true
    },
    proccessFinancial () {
      this.cart.financial = {
        totalPrice: 0 as number,
        openValue: 0 as number,
        paidValue: 0 as number,
        additionValue: 0 as number
      }
      this.cart.financial.totalPrice = this.utilFinancial.getTotalPrice(this.cart.orderItemList)
      this.cart.financial.paidValue = this.utilFinancial.getPaidValue(this.cart.billToReceiveList)
      this.cart.financial.openValue = this.utilFinancial.getOpenValue(this.cart.financial?.paidValue, this.cart.financial?.totalPrice)
    },
    enrichBillToReceiveModel () {
      var model = {
        identifier: null as any,
        title: 'PGTO',
        emissionDate: moment().format('YYYY-MM-DD'),
        type: Object.keys(TypeOfBillToReceiveEnum)[Object.values(TypeOfBillToReceiveEnum).indexOf(TypeOfBillToReceiveEnum.SALE as unknown as TypeOfBillToReceiveEnum)],
        value: this.cart.financial.openValue,
        customer: this.cart.customer,
        status: Object.keys(StatusOfBillEnum)[Object.values(StatusOfBillEnum).indexOf(StatusOfBillEnum.OPENED as unknown as StatusOfBillEnum)],
        installmentList: [] as Array<any>
      }
      return model
    },
    getSpecification (grateItemList: []) {
      let ret = ''
      grateItemList.forEach((grateItem: any) => {
        ret += ` | ${grateItem.grate.description}: ${grateItem.value}`
      })
      return ret
    },
    addProduct (itemEdit: any) {
      this.cart.orderItemList.push(itemEdit)
    },
    onSelectCustomer (select: any) {
      this.cart.customer = select[0]
    },
    addPayment (billToReceive: any) {
      console.log(this.cart)
      billToReceive.title = billToReceive.title.toString().concat('-').concat(this.cart.billToReceiveList.length + 1)
      this.cart.billToReceiveList.push(billToReceive)
      this.proccessFinancial()
    },
    showCustomerDialog () {
      this.customerDialog = true
      this.customerDialogKey++
    },
    showPaymentDialog () {
      this.paymentDialog = true
      this.paymentDialogKey++
    },
    showAlertDialog () {
      this.alertDialog = true
      this.alertDialogKey++
    },
    async searchCustomers (search: any) {
      var payload = {} as any
      await httpAPI.post('/customer/search', search)
        .then((response) => {
          payload.itemList = Object.keys(response.data.data.result).map(index => {
            const item = {
              identifier: response.data.data.result[index].identifier,
              name: response.data.data.result[index].name
            }
            return item
          })
          payload.total = response.data.data.total
          payload.loading = false
        })
      return payload
    },
    async getOrder () {
      if (this.cart.identifier) {
        const search = {
          example: {
            identifier: this.cart.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/cart/search', search)
        this.cart = response.data.data.result[0]
      }
    },
    async getProductList () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/product/search', search)
      this.productList = response.data.data.result
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
    if (this.cart.identifier) {
      this.getOrder()
    }
  }
})
</script>

<style scoped>
.pointer :hover {
  cursor: pointer;
}
</style>
