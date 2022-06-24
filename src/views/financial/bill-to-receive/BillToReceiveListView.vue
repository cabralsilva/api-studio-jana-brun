<template>
    <div>
      <v-row>
        <v-col align="right">
          <v-btn @click="navigation('/panel/financial/bill-to-receive/edit')" icon middle>
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
          <bill-to-receive-data-table-view></bill-to-receive-data-table-view>
        </v-card-text>
      </v-card>
    </div>
</template>

<script lang="ts">
import Vue from 'vue'
import BillToReceiveDataTableView from '@/views/financial/bill-to-receive/BillToReceiveDataTableView.vue'

export default Vue.extend({
  components: {
    BillToReceiveDataTableView
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
          text: 'Contas a receber',
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
