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
                    label="Forma de pagamento"
                    :rules="[validator.requiredObject]"
                    v-model="payment.method"
                    :items="paymentMethodList"
                    item-text="description"
                    item-value="identifier"
                    return-object/>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-text-field readonly label="Valor" v-model="payment.value"/>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <input-date label="Data pagamento" startIn="DATE" :callback="(date) => payment.paymentDate = date" :initDate="payment.paymentDate"/>
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
import moment from 'moment'
import { Validations } from '@/lib/Utils'

export default Vue.extend({
  name: 'PaymentDialog',
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
        code: '',
        method: null as any,
        paymentDate: moment().format('YYYY-MM-DD'),
        value: 0.00,
        installment: null as any
      } as any)
    }
  },
  data () {
    return {
      validator: new Validations(),
      show: this.pShow,
      selected: [],
      paymentMethodList: [],
      payment: this.pModel
    }
  },
  methods: {
    async confirmPayment () {
      if ((this.$refs.formModal as Vue & { validate: () => boolean }).validate()) {
        await this.callback(this.payment)
        this.show = false
      }
    },
    async getPaymentMethodList () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/payment-method/search', search)
      this.paymentMethodList = response.data.data.result
    }
  },
  async created () {
    await this.getPaymentMethodList()
  }
})
</script>
