<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formCondition" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="paymentCondition.code" :rules="[required]" label="Código"/>
            </v-col>
            <v-col>
              <v-text-field v-model="paymentCondition.description" :rules="[required]" label="Descrição"/>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-text-field v-model="paymentCondition.quantityInstallments" :rules="[required, onlyNumber]" label="Número de parcelas"/>
            </v-col>
            <v-col>
              <v-text-field v-model="paymentCondition.additionPercent" :rules="[required, onlyNumber]" label="Acréscimo/Descontos (%)"/>
            </v-col>
            <v-col>
              <v-select v-model="paymentCondition.status" :rules="[requiredSelect]" :items="statusActiveList" item-text="value" item-value="key" label="Status" />
            </v-col>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/register/financial/payment-condition')"> Cancelar </v-btn>
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

export default Vue.extend({
  components: {
  },
  data () {
    return {
      statusActiveList: StatusActiveList,
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      paymentCondition: {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        quantityInstallments: null,
        additionPercent: null,
        status: null
      }
    }
  },
  watch: {
  },
  methods: {
    required (value: any) {
      if (isNaN(value)) {
        return !!value || 'Required.'
      }

      return value >= 0 || 'Required.'
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
      if ((this.$refs.formCondition as Vue & { validate: () => boolean }).validate()) {
        try {
          if (this.paymentCondition.identifier) {
            await httpAPI.put('/payment-condition', this.paymentCondition)
          } else {
            await httpAPI.post('/payment-condition', this.paymentCondition)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          this.navigation('/panel/register/financial/payment-condition')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    clear () {
      this.paymentCondition = {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        quantityInstallments: null,
        additionPercent: null,
        status: null
      }
    },
    async getPaymentCondition () {
      if (this.paymentCondition.identifier) {
        const search = {
          columnList: [
            'identifier',
            'code',
            'description',
            'quantityInstallments',
            'additionPercent',
            'status'
          ],
          resultUnique: true,
          example: {
            identifier: this.paymentCondition.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/payment-condition/search', search)
        this.paymentCondition = response.data.data.result[0]
      }
    }
  },
  created () {
    if (this.paymentCondition.identifier) {
      this.getPaymentCondition()
    }
  }
})
</script>
