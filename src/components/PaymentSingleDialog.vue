<template>
  <v-row justify="center">
    <v-form ref="formModal" lazy-validation>
      <v-dialog v-model="show" max-width="600px">
        <v-card>
          <v-card-title>
            <span class="text-h5">Pagamento</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-select
                    label="Condição de pagamento"
                    :rules="[validator.requiredObject]"
                    v-model="billToReceive.paymentCondition"
                    :items="paymentConditionList"
                    item-text="description"
                    item-value="identifier"
                    return-object
                    @change="1"/>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field label="Valor" v-model="billToReceive.value"/>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <input-date label="Data 1º pgto" startIn="DATE" :callback="(date) => billToReceive.emissionDate = date" :initDate="billToReceive.emissionDate"/>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="secondary" @click="confirmPayment()">
              <v-icon>mdi-cash-multiple</v-icon>
              Confirmar
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-form>
  </v-row>
</template>

<script lang="ts">
import Vue from 'vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'
import InputDate from '@/components/InputDate.vue'
import { TypeOfBillToReceiveEnum } from '@/model/TypeOfBillToReceiveEnum'
import { StatusOfBillEnum } from '@/model/StatusOfBillEnum'
import moment from 'moment'
import { Validations } from '@/lib/Utils'

export default Vue.extend({
  name: 'SelectDialog',
  components: {
    InputDate
  },
  props: {
    pShow: {
      type: Boolean,
      default: false
    },
    pTitle: {
      type: String,
      default: 'Novo pagamento'
    },
    callback: {
      type: Function,
      required: true
    },
    pModel: {
      type: Object as any,
      default: () => ({
        identifier: null as any,
        title: 'AV',
        emissionDate: moment(),
        type: Object.keys(TypeOfBillToReceiveEnum)[Object.values(TypeOfBillToReceiveEnum).indexOf(TypeOfBillToReceiveEnum.SINGLE as unknown as TypeOfBillToReceiveEnum)],
        value: 0.00,
        customer: null as any,
        status: Object.keys(StatusOfBillEnum)[Object.values(StatusOfBillEnum).indexOf(StatusOfBillEnum.OPENED as unknown as StatusOfBillEnum)],
        installmentList: [] as Array<any>
      } as any)
    }
  },
  data () {
    return {
      validator: new Validations(),
      show: this.pShow,
      selected: [],
      paymentConditionList: [],
      billToReceive: this.pModel
    }
  },
  methods: {
    confirmPayment () {
      if ((this.$refs.formModal as Vue & { validate: () => boolean }).validate()) {
        this.show = false
        this.callback(this.billToReceive)
      }
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
  watch: {

  },
  created () {
    this.getPaymentConditionList()
    console.log(this.billToReceive)
  }
})
</script>
