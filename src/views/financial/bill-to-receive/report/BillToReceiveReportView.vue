<template>
  <div>
    <v-row>
      <v-col>
        <v-card class="pa-2">
          <v-form ref="formFilter" lazy-validation>
            <v-row>
              <v-col>
                <input-date label="De" startIn="DATE" :callback="(date) => formSearchFilter.initTargetDate = date" :initDate="formSearchFilter.initTargetDate"/>
              </v-col>
              <v-col>
                <input-date label="Até" startIn="DATE" :callback="(date) => formSearchFilter.endTargetDate = date" :initDate="formSearchFilter.endTargetDate"/>
              </v-col>
              <v-col>
                <v-select label="Pessoa/Cliente" v-model="formSearchFilter.personInList" :items="personInList" item-text="name" item-value="identifier" return-object multiple/>
              </v-col>
              <v-col>
                <v-select label="Situação" v-model="formSearchFilter.statusInList"
                  :items="statusInList" item-text="value" item-value="key" multiple/>
              </v-col>
              <v-col style="text-align: end;">
                <v-btn color="primary" :loading="searchLoading" :style="{transform:'translateY(50%)'}" @click="search"> Pesquisar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-card>
      </v-col>
    </v-row>
    <!-- <v-row v-if="reportItemList.length > 0" class="px-2" style="text-align: end;">
      <v-col>
        <v-btn color="secondary" @click="1"> Imprimir </v-btn>
      </v-col>
    </v-row> -->
    <v-row>
      <v-col>
        <v-data-table
          :headers="reportHeaders"
          :items="reportItemList"
          hide-default-footer
          class="elevation-1"
        >
          <template v-slot:[`item.status`]="{ item }">
            <v-chip :color="getColorStatus(item)" dark>
              {{ statusOfBillEnum[item.status] }}
            </v-chip>
          </template>
          <template v-slot:[`item.actions`]="{ item }">
            <v-icon v-if="!isSettled(item)" class="ma-2" @click="openAddPayment(item)">mdi-currency-usd</v-icon>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
    <payment-dialog
      :key="paymentDialogKey"
      :callback="addPayment"
      :pShow="paymentDialog"
      :pModel="paymentEdit"/>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'
import InputDate from '@/components/InputDate.vue'
import PaymentDialog from '@/components/PaymentDialog.vue'
import { StatusOfBillList, StatusOfBillEnum } from '@/model/StatusOfBillEnum'
import moment from 'moment'

export default Vue.extend({
  components: {
    InputDate,
    PaymentDialog
  },
  data () {
    return {
      paymentDialog: false,
      paymentDialogKey: 1,
      formSearchFilter: {
        initTargetDate: moment().format('YYYY-MM-DD'),
        endTargetDate: moment().format('YYYY-MM-DD'),
        statusInList: [] as any,
        personInList: [] as any,
        paymentConditionInList: [] as any
      },
      statusInList: StatusOfBillList,
      statusOfBillEnum: StatusOfBillEnum,
      personInList: [] as any,
      paymentConditionInList: [] as any,
      reportItemList: [] as any,
      searchLoading: false,
      reportHeaders: [
        { text: 'Descrição', value: 'description' },
        { text: 'Pessoa/Cliente', value: 'billToReceive.customer.name' },
        { text: 'Valor', value: 'value' },
        { text: 'Vencimento', value: 'targetDate' },
        { text: 'Situação', value: 'status' },
        { text: 'Ações', value: 'actions', sortable: false }
      ],
      paymentEdit: {} as any
    }
  },
  watch: {
  },
  methods: {
    isSettled (item: any) {
      return item.status === Object.keys(StatusOfBillEnum)[Object.values(StatusOfBillEnum).indexOf(StatusOfBillEnum.SETTLED as unknown as StatusOfBillEnum)]
    },
    async addPayment (payment: any) {
      const response = await httpAPI.post('/bill-to-receive-installment-payment', payment)
      this.search()
    },
    openAddPayment (installment: any) {
      this.paymentEdit = {
        installment: installment,
        value: installment.value,
        paymentDate: moment().format('YYYY-MM-DD')
      }
      this.paymentDialog = true
      this.paymentDialogKey++
    },
    getColorStatus (item: any) {
      if (item.status === Object.keys(StatusOfBillEnum)[Object.values(StatusOfBillEnum).indexOf(StatusOfBillEnum.SETTLED as unknown as StatusOfBillEnum)]) {
        return 'green'
      }

      if (item.status === Object.keys(StatusOfBillEnum)[Object.values(StatusOfBillEnum).indexOf(StatusOfBillEnum.OPENED as unknown as StatusOfBillEnum)]) {
        return 'orange'
      }

      return 'blue'
    },
    async getPersonInList () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/person/search', search)
      this.personInList = response.data.data.result
    },
    async getPaymentConditionInList () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/payment-condition/search', search)
      this.paymentConditionInList = response.data.data.result
    },
    async search () {
      this.searchLoading = true
      console.log(this.formSearchFilter)
      const search = {
        ...this.formSearchFilter,
        customSearch: true,
        example: {},
        orderColumn: 'targetDate',
        asc: true,
        columnList: ['identifier', 'description', 'number', 'billToReceive.customer.name', 'targetDate', 'status', 'value'],
        pageable: false
      }
      const response = await httpAPI.post('/bill-to-receive-installment/search', search)
      this.reportItemList = response.data.data.result
      console.log(this.reportItemList)
      this.searchLoading = false
    }
  },
  created () {
    this.getPersonInList()
    this.getPaymentConditionInList()
  }
})
</script>
