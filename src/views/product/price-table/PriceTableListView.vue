<template>
    <div>
      <v-row>
        <v-col align="right">
          <v-btn @click="navigation('/panel/register/product/price-table/edit')" icon middle>
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
          <price-table-data-table-view></price-table-data-table-view>
        </v-card-text>
      </v-card>
    </div>
</template>

<script lang="ts">
import Vue from 'vue'
import PriceTableDataTableView from '@/views/product/price-table/PriceTableDataTableView.vue'

export default Vue.extend({
  components: {
    PriceTableDataTableView
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
          text: 'Produto',
          disabled: true,
          href: ''
        },
        {
          text: 'Tabela de preço',
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
