<template>
    <div>
      <v-card class="pa-4">
        <v-form ref="formBillToReceive" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="billToReceive.title" :rules="[required]" label="Título/Descrição"/>
            </v-col>
            <v-col>
              <v-select :readonly="!!billToReceive.matriculation" v-model="billToReceive.customer" :rules="[requiredSelect]" :items="customerList" item-text="name" item-value="identifier" return-object label="Cliente" />
            </v-col>
            <v-col>
              <v-text-field v-model="billToReceive.value" :rules="[required, number]" label="Valor"/>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <input-date startIn="DATE" :callback="(date) => billToReceive.emissionDate = date" :initDate="billToReceive.emissionDate" label="Data 1º Parcela" />
            </v-col>
            <v-col>
              <v-select
                v-model="billToReceive.paymentCondition"
                :rules="[requiredSelect]"
                :items="paymentConditionList"
                item-text="description"
                item-value="identifier"
                return-object
                @change="createInstallment()"
                label="Condição de pagamento" />
            </v-col>
            <v-col>
              <v-select :readonly="!!billToReceive.matriculation" :rules="[requiredSelect]" v-model="billToReceive.type" :items="typeOfBillToReceiveList" item-text="value" item-value="key" label="Tipo" />
            </v-col>
          </v-row>
          <template v-if="billToReceive.paymentCondition">
            <v-row>
              <v-col>
                <v-simple-table>
                  <template v-slot:default>
                    <thead>
                      <tr>
                        <th>Descrição</th>
                        <th>Valor base</th>
                        <th class="text-center">Acréscimos/Descontos</th>
                        <th>Valor final</th>
                        <th>Vencimento</th>
                        <th>Situação</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(installment, index) of installmentList" :key="installment.identifier">
                        <td>
                          {{ `${installment.description}` }}
                        </td>
                        <td>
                          R$ {{ getValueFinancial(installment.originalValue) }}
                        </td>
                        <td class="text-center">
                          <div class="row">
                            <div class="offset-4 col-4 align-self-center">
                              <v-text-field v-if="getNextInstallmentOpened() == index" v-model="installment.additionValue" :rules="[numberInteger]"/>
                            </div>
                          </div>
                        </td>
                        <td>
                          <b>R$ {{ getInstallmentValue(installment) }}</b>
                        </td>
                        <td>
                          {{ getMomentPlusMonth(billToReceive.emissionDate, index) }}
                        </td>
                        <td>
                          <v-chip color="blue" dark>
                            {{ getStatusDescription(installment.status) }}
                          </v-chip>
                        </td>
                      </tr>
                    </tbody>
                  </template>
                </v-simple-table>
              </v-col>
            </v-row>
          </template>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/financial/bill-to-receive')"> Cancelar </v-btn>
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
import { TypeOfValueList, TypeOfValueEnum } from '@/model/TypeOfValueEnum'
import InputDate from '@/components/InputDate.vue'
import { TypeOfBillToReceiveList, TypeOfBillToReceiveEntries, TypeOfBillToReceiveEnum } from '@/model/TypeOfBillToReceiveEnum'
import { StatusOfBillList, StatusOfBillType, StatusOfBillEnum } from '@/model/StatusOfBillEnum'
import moment from 'moment'

export default Vue.extend({
  name: 'BillToReceiveForm',
  props: {
    discountFirstInstallment: {
      type: Boolean,
      default: false
    },
    dialog: {
      type: Boolean,
      default: false
    },
    callback: {
      type: Function
    },
    indexTitle: {
      type: Number,
      default: 1
    },
    matriculation: {
      default: null as any
    },
    maxValue: {
      type: Number,
      default: 0
    }
  },
  components: {
    InputDate
  },
  data () {
    return {
      typeOfBillToReceiveList: TypeOfBillToReceiveList,
      statusActiveList: StatusActiveList,
      TypeOfValueList: TypeOfValueList,
      moment: moment(),
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      billToReceive: {
        identifier: null as any,
        title: null as any,
        emissionDate: null,
        type: null as any,
        value: 0,
        paymentCondition: {
          quantityInstallments: 0
        },
        customer: null as any,
        matriculation: null as any,
        status: null,
        installmentList: [] as Array<any>
      },
      installmentList: [] as Array<any>,
      customerList: [],
      paymentConditionList: [],
      headers: [
        { text: 'Descrição', value: 'description' },
        { text: 'Título', value: 'title' },
        { text: 'Valor', value: 'value' },
        { text: 'Vencimento', value: 'dueDate' },
        { text: 'Situação', value: 'status' }
      ]
    }
  },
  watch: {
  },
  methods: {
    getInstallmentValue (installment: any) {
      return this.getValueFinancial(Number.parseFloat(installment.originalValue) + Number.parseFloat(installment.additionValue))
    },
    getValueFinancial (value: any): number {
      console.log('financial ', value)
      return Number(Number.parseFloat(value).toFixed(2))
    },
    createInstallment () {
      const installments = []
      let remainigValue = this.getValueFinancial(this.billToReceive.value)
      for (let index = 1; index <= this.billToReceive.paymentCondition.quantityInstallments; index++) {
        let installmentValue = this.getValueFinancial(this.billToReceive.value / this.billToReceive.paymentCondition.quantityInstallments)
        if (index === this.billToReceive.paymentCondition.quantityInstallments) {
          installmentValue = remainigValue
        }
        remainigValue = this.getValueFinancial(remainigValue - installmentValue)

        let additionValue = this.getValueFinancial(0)
        if (index === 1 && this.discountFirstInstallment) {
          additionValue = this.getValueFinancial(installmentValue / -2)
        }

        installments.push({
          title: `${index}/${this.billToReceive.paymentCondition.quantityInstallments}`,
          description: `${this.billToReceive.title} - ${index}/${this.billToReceive.paymentCondition.quantityInstallments}`,
          number: index,
          status: Object.keys(StatusOfBillEnum)[Object.values(StatusOfBillEnum).indexOf(StatusOfBillEnum.OPENED as unknown as StatusOfBillEnum)],
          targetDate: moment(this.billToReceive.emissionDate).add(index - 1, 'months'),
          value: installmentValue,
          additionValue: additionValue,
          originalValue: installmentValue
        })
        console.log(installments)
      }

      this.installmentList = installments
    },
    getMomentPlusMonth (date: any, addMonth: any) {
      return moment(date).add(addMonth, 'months').format('DD/MM/YYYY')
    },
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
    numberInteger (value: any) {
      if (value && value.length > 0) {
        const lastChar = value.charAt(value.length - 1)
        if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
          value = value.slice(0, -1)
        }
        const valueSplit = value.split('.')
        if (valueSplit.length > 2) {
          valueSplit.splice(2)
          value = valueSplit.join('.')
        }

        if (valueSplit[1] && valueSplit[1].length > 2) {
          valueSplit[1] = valueSplit[1].substring(0, 2)
          value = valueSplit.join('.')
        }

        if (!value.match(/^-?\d{0,5}(?:\.\d{1,2})?$/)) {
          return 'Invalid format'
        }
      }
      return true
    },
    number (value: any) {
      if (value && value.length > 0) {
        const lastChar = value.charAt(value.length - 1)
        if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
          this.billToReceive.value = value.slice(0, -1)
        }
        const valueSplit = value.split('.')
        if (valueSplit.length > 2) {
          valueSplit.splice(2)
          this.billToReceive.value = valueSplit.join('.')
          value = this.billToReceive.value
        }

        if (valueSplit[1] && valueSplit[1].length > 2) {
          valueSplit[1] = valueSplit[1].substring(0, 2)
          this.billToReceive.value = valueSplit.join('.')
          value = this.billToReceive.value
        }

        if (!value.match(/^\d{0,5}(?:\.\d{1,2})?$/)) {
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
    getStatusDescription (status: any) {
      return StatusOfBillEnum[status as StatusOfBillType]
    },
    getNextInstallmentOpened () {
      let index = -1
      this.installmentList.forEach((installment, i) => {
        if (installment.status === Object.keys(StatusOfBillEnum)[Object.values(StatusOfBillEnum).indexOf(StatusOfBillEnum.OPENED as unknown as StatusOfBillEnum)]) {
          if (index >= 0) {
            return
          }
          index++
        }
      })
      return index
    },
    async save () {
      if ((this.$refs.formBillToReceive as Vue & { validate: () => boolean }).validate()) {
        this.billToReceive.installmentList = this.installmentList
        try {
          if (this.billToReceive.identifier) {
            await httpAPI.put('/bill-to-receive', this.billToReceive)
          } else {
            await httpAPI.post('/bill-to-receive', this.billToReceive)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          if (this.callback) {
            this.callback()
            return
          }
          this.navigation('/panel/financial/bill-to-receive')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    async getBillToReceive () {
      if (this.billToReceive.identifier) {
        const search = {
          example: {
            identifier: this.billToReceive.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/bill-to-receive/search', search)
        this.billToReceive = response.data.data.result[0]
        this.installmentList = await this.getInstallmentList()
      }
    },
    async getCustomerList () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/customer/search', search)
      this.customerList = response.data.data.result
    },
    async getInstallmentList () {
      const search = {
        example: {
          billToReceive: {
            identifier: this.billToReceive.identifier
          }
        },
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/bill-to-receive-installment/search', search)
      return response.data.data.result
    },
    async getPaymentConditionList () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/payment-condition/search', search)
      this.paymentConditionList = response.data.data.result
    }
  },
  created () {
    this.billToReceive.identifier = this.$route.path.includes('bill-to-receive/edit') ? this.$route.params.identifier : undefined
    this.billToReceive.matriculation = this.matriculation
    if (this.billToReceive.identifier) {
      this.getBillToReceive()
    }
    this.getCustomerList()
    this.getPaymentConditionList()

    if (this.matriculation?.identifier) {
      this.billToReceive.type = Object.keys(TypeOfBillToReceiveEnum)[Object.values(TypeOfBillToReceiveEnum).indexOf(TypeOfBillToReceiveEnum.MATRICULATION as unknown as TypeOfBillToReceiveEnum)]
      this.billToReceive.value = this.maxValue
      this.billToReceive.customer = this.billToReceive.matriculation.responsibleFinancial
      this.billToReceive.title = `MAT${this.billToReceive.matriculation.identifier}-${this.indexTitle}`
    }
  },
  destroyed () {
    console.log('DESTROYED COMPONENT')
  },
  mounted () {
    console.log('MOUNTED COMPONENT')
  },
  deactivated () {
    console.log('DEACTIVED COMPONENT')
  }
})
</script>
