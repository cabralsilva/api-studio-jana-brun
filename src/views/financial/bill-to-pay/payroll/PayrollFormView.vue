<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formPayroll" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field label="Descrição" v-model="payroll.description" :rules="[required]"/>
            </v-col>
            <v-col>
              <input-date label="Data de pagamento" startIn="DATE" :callback="(date) => payroll.targetDate = date" :initDate="payroll.targetDate"/>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <input-date label="Data inicial" startIn="DATE" :callback="(date) => payroll.initDate = date" :initDate="payroll.initDate"/>
            </v-col>
            <v-col>
              <input-date label="Data final" startIn="DATE" :callback="(date) => payroll.endDate = date" :initDate="payroll.endDate"/>
            </v-col>
          </v-row>
          <v-divider />
          <v-row>
            <v-col>

              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left">
                        Funcionário
                      </th>
                      <th class="text-middle">
                        Pagamentos
                      </th>
                      <th class="text-left">
                        Total
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(value, key) in payroll.payrollDetailGrouped" :key="key">
                      <td>
                        <b>{{ key }}</b><br/>
                      </td>
                      <td>
                        <v-row>
                          <v-col v-for="payrollDetail of value" :key="payrollDetail.identifier">
                            <v-card class="pa-0" outlined  flat color="transparent">
                              <v-card-text class="pa-0">
                                <span class="subheading">{{ payrollDetail.description }}</span>
                                <v-chip-group active-class="deep-purple--text text--accent-4">
                                  <v-chip class="ma-2">
                                    {{ `${payrollDetail.value}`}}
                                  </v-chip>
                                </v-chip-group>
                              </v-card-text>
                            </v-card>
                          </v-col>
                        </v-row>
                      </td>
                      <td>
                        <b>{{ value.reduce((acc, obj) => { return acc + obj.value }, 0) }}</b>
                      </td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </v-col>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn class="mx-1" text @click="navigation('/panel/financial/bill-to-pay/payroll')"> Voltar </v-btn>
              <v-btn class="mx-1" v-if="isAwaitingApprove()" color="success"  @click="confirm"> Aprovar </v-btn>
              <v-btn class="mx-1" v-if="!isConfirmed()" color="primary"  @click="process" :loading="processing"> Processar </v-btn>
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
import InputDate from '@/components/InputDate.vue'
import { StatusOfPayrollEnum } from '@/model/StatusOfPayrollEnum'

export default Vue.extend({
  components: {
    InputDate
  },
  data () {
    return {
      statusEnum: StatusOfPayrollEnum,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      payroll: {
        identifier: this.$route.params.identifier,
        description: null as any,
        status: null as any,
        targetDate: null as any,
        initDate: null as any,
        endDate: null as any,
        payrollDetailList: [] as any,
        payrollDetailGrouped: null as any
      },
      processing: false
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
    groupBy (list: []) {
      return list.reduce((acc: any, currentValue: any) => {
        const groupKey = currentValue.employee.person.name
        if (!acc[groupKey]) {
          acc[groupKey] = []
        }
        acc[groupKey].push(currentValue)
        return acc
      }, {})
    },
    async process () {
      this.processing = true
      if ((this.$refs.formPayroll as Vue & { validate: () => boolean }).validate()) {
        try {
          var response = await httpAPI.post('/payroll/process', this.payroll)
          this.payroll = response.data.data
          this.payroll.payrollDetailGrouped = this.groupBy(response.data.data.payrollDetailList)
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
        this.processing = false
      }
    },
    async confirm () {
      if (this.payroll.identifier) {
        const response = await httpAPI.patch(`/payroll/checkout/${this.payroll.identifier}`)
        this.navigation('/panel/financial/bill-to-pay/payroll')
      }
    },
    async getPayroll () {
      if (this.payroll.identifier) {
        const search = {
          example: {
            identifier: this.payroll.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/payroll/search', search)
        this.payroll = response.data.data.result[0]
        this.payroll.payrollDetailGrouped = this.groupBy(this.payroll.payrollDetailList)
      }
    },
    isAwaitingApprove () {
      return this.payroll.status === Object.keys(StatusOfPayrollEnum)[Object.values(StatusOfPayrollEnum).indexOf(StatusOfPayrollEnum.AWAITING_APPROVE as unknown as StatusOfPayrollEnum)]
    },
    isConfirmed () {
      return this.payroll.status === Object.keys(StatusOfPayrollEnum)[Object.values(StatusOfPayrollEnum).indexOf(StatusOfPayrollEnum.CONFIRMED as unknown as StatusOfPayrollEnum)]
    }
  },
  async created () {
    if (this.payroll.identifier) {
      await this.getPayroll()
      console.log(this.payroll)
    }
  }
})
</script>
