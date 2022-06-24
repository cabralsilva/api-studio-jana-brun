<template>
    <div>
      <v-row>
        <v-col align="right">
          <v-btn @click="navigation('/panel/financial/bill-to-pay/payroll/edit')" icon middle>
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-col>
      </v-row>
      <v-breadcrumbs :items="breadcrumbs">
        <template v-slot:divider>
          <v-icon>mdi-chevron-right</v-icon>
        </template>
      </v-breadcrumbs>
      <v-card>
        <v-card-text>
          <payroll-data-table-view />
        </v-card-text>
      </v-card>
    </div>
</template>

<script lang="ts">
import Vue from 'vue'
import PayrollDataTableView from '@/views/financial/bill-to-pay/payroll/PayrollDataTableView.vue'

export default Vue.extend({
  components: {
    PayrollDataTableView
  },
  data () {
    return {
      breadcrumbs: [
        {
          text: 'Home',
          disabled: false,
          href: '/panel'
        },
        {
          text: 'Financeiro',
          disabled: true,
          href: ''
        },
        {
          text: 'Contas a pagar',
          disabled: true,
          href: ''
        },
        {
          text: 'Folha de pagamento',
          disabled: true,
          href: ''
        }
      ]
    }
  },
  methods: {
    navigation (to: any) {
      if (to) {
        // eslint-disable-next-line
        this.$router.push(to).catch(err => { })
      }
    }
  }
})
</script>
