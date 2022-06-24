<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formBillToPay" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="billToPay.title" :rules="[required]" label="Título/Descrição"/>
            </v-col>
            <v-col>
              <v-select v-model="billToPay.person" :rules="[requiredSelect]" :items="personList" item-text="name" item-value="identifier" return-object label="Pessoa/Fornecedor" />
            </v-col>
            <v-col>
              <v-select v-model="billToPay.paymentCondition" :rules="[requiredSelect]" :items="paymentConditionList" item-text="description" item-value="identifier" return-object label="Condição de pagamento" />
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <input-date startIn="DATE" :callback="(date) => billToPay.emissionDate = date" :initDate="billToPay.emissionDate" label="Data 1º Parcela" />
            </v-col>
            <v-col>
              <v-text-field v-model="billToPay.value" :rules="[required, number]" label="Valor"/>
            </v-col>
          </v-row>
          <template v-if="billToPay.paymentCondition">
            <v-row>
              <v-col>
                <v-data-table
                  :headers="headers"
                  :items="getInstallments()"
                  hide-default-footer
                  class="elevation-1"
                >
                  <template v-slot:[`item.status`]="{ item }">
                    <v-chip color="blue" dark>
                      {{ item.status }}
                    </v-chip>
                  </template>
                </v-data-table>
              </v-col>
            </v-row>
          </template>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/financial/bill-to-pay')"> Cancelar </v-btn>
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
import moment from 'moment'

export default Vue.extend({
  components: {
    InputDate
  },
  data () {
    return {
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
      billToPay: {
        identifier: this.$route.params.identifier,
        title: null,
        emissionDate: null,
        value: 0,
        paymentCondition: {
          quantityInstallments: 0
        },
        person: {},
        status: null
      },
      personList: [],
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
    getValueFinancial (value: any): number {
      return Number(Number.parseFloat(value).toFixed(2))
    },
    getInstallments () {
      const installments = []
      let remainigValue = this.getValueFinancial(this.billToPay.value)
      for (let index = 1; index <= this.billToPay.paymentCondition.quantityInstallments; index++) {
        let installmentValue = this.getValueFinancial(this.billToPay.value / this.billToPay.paymentCondition.quantityInstallments)
        if (index === this.billToPay.paymentCondition.quantityInstallments) {
          installmentValue = remainigValue
        }
        remainigValue = this.getValueFinancial(remainigValue - installmentValue)
        installments.push({
          description: `${index}º parcela`,
          title: `${index}/${this.billToPay.paymentCondition.quantityInstallments}`,
          value: installmentValue,
          dueDate: this.getMomentPlusMonth(this.billToPay.emissionDate, index),
          status: 'Aberto'
        })
      }

      return installments
    },
    getMomentPlusMonth (date: any, addMonth: any) {
      console.log(date)
      return moment(date).add(addMonth - 1, 'months').format('DD/MM/YYYY')
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
    number (value: any) {
      if (value && value.length > 0) {
        const lastChar = value.charAt(value.length - 1)
        if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
          this.billToPay.value = value.slice(0, -1)
        }
        const valueSplit = value.split('.')
        if (valueSplit.length > 2) {
          valueSplit.splice(2)
          this.billToPay.value = valueSplit.join('.')
          value = this.billToPay.value
        }

        if (valueSplit[1] && valueSplit[1].length > 2) {
          valueSplit[1] = valueSplit[1].substring(0, 2)
          this.billToPay.value = valueSplit.join('.')
          value = this.billToPay.value
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
    async save () {
      if ((this.$refs.formBillToPay as Vue & { validate: () => boolean }).validate()) {
        console.log('passou: ', this.billToPay)
        try {
          if (this.billToPay.identifier) {
            await httpAPI.put('/bill-to-pay', this.billToPay)
          } else {
            await httpAPI.post('/bill-to-pay', this.billToPay)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          this.navigation('/panel/financial/bill-to-pay')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    clear () {
      this.billToPay = {
        identifier: this.$route.params.identifier,
        title: null,
        emissionDate: null,
        value: 0,
        paymentCondition: {
          quantityInstallments: 0
        },
        person: {},
        status: null
      }
    },
    async getBillToPay () {
      if (this.billToPay.identifier) {
        const search = {
          example: {
            identifier: this.billToPay.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/bill-to-pay/search', search)
        this.billToPay = response.data.data.result[0]
      }
    },
    async getSupplierList () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/person/search', search)
      this.personList = response.data.data.result
      console.log(this.personList)
    },
    async getPaymentConditionList () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/payment-condition/search', search)
      this.paymentConditionList = response.data.data.result
      console.log(this.paymentConditionList)
    }
  },
  created () {
    if (this.billToPay.identifier) {
      this.getBillToPay()
    }
    this.getSupplierList()
    this.getPaymentConditionList()
  }
})
</script>
