<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formNotice" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="paymentMethod.code" :rules="[required]" label="Código"/>
              <v-select v-model="paymentMethod.currencyType" :rules="[requiredSelect]" :items="TypeOfCurrencyList" item-text="value" item-value="key" label="Tipo de moeda" />
            </v-col>
            <v-col>
              <v-text-field v-model="paymentMethod.description" :rules="[required]" label="Descrição"/>
              <v-select v-model="paymentMethod.status" :rules="[requiredSelect]" :items="statusActiveList" item-text="value" item-value="key" label="Status" />
            </v-col>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/register/financial/payment-method')"> Cancelar </v-btn>
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
import { TypeOfCurrencyList } from '@/model/TypeOfCurrencyEnum'

export default Vue.extend({
  components: {
  },
  data () {
    return {
      statusActiveList: StatusActiveList,
      TypeOfCurrencyList: TypeOfCurrencyList,
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      paymentMethod: {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        currencyType: null,
        status: null
      }
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
    navigation (to: any) {
      // eslint-disable-next-line
      this.$router.push(to).catch(_err => { })
    },
    async save () {
      if ((this.$refs.formNotice as Vue & { validate: () => boolean }).validate()) {
        console.log('passou: ', this.paymentMethod)
        try {
          if (this.paymentMethod.identifier) {
            await httpAPI.put('/payment-method', this.paymentMethod)
          } else {
            await httpAPI.post('/payment-method', this.paymentMethod)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          this.navigation('/panel/register/financial/payment-method')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    clear () {
      this.paymentMethod = {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        currencyType: null,
        status: null
      }
    },
    async getPaymentMethod () {
      if (this.paymentMethod.identifier) {
        const search = {
          columnList: [
            'identifier',
            'code',
            'description',
            'currencyType',
            'status'
          ],
          resultUnique: true,
          example: {
            identifier: this.paymentMethod.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/payment-method/search', search)
        this.paymentMethod = response.data.data.result[0]
      }
    }
  },
  created () {
    if (this.paymentMethod.identifier) {
      this.getPaymentMethod()
    }
  }
})
</script>
