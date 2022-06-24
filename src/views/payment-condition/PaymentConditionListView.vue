<template>
    <div>
      <v-row>
        <v-col align="right">
          <v-btn @click="navigation('/panel/register/financial/payment-condition/edit')" icon middle>
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
          <payment-condition-data-table-view></payment-condition-data-table-view>
        </v-card-text>
      </v-card>
    </div>
</template>

<script lang="ts">
import Vue from 'vue'
import PaymentConditionDataTableView from '@/views/payment-condition/PaymentConditionDataTableView.vue'

export default Vue.extend({
  components: {
    PaymentConditionDataTableView
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
          text: 'Cadastro',
          disabled: true,
          href: ''
        },
        {
          text: 'Financeiro',
          disabled: true,
          href: ''
        },
        {
          text: 'Condição de pagamento',
          disabled: true,
          href: ''
        }
      ],
      noticeList: []
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
