<template>
  <div>
    <v-row>
      <h3 class="pa-4">Grade</h3>
      <v-col align="right">
        <v-btn @click="navigation('/panel/register/product/grate/edit')" icon middle>
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
        <grate-data-table-view></grate-data-table-view>
      </v-card-text>
    </v-card>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import GrateDataTableView from '@/views/product/grate/GrateDataTableView.vue'

export default Vue.extend({
  components: {
    GrateDataTableView
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
          text: 'Product',
          disabled: true,
          href: ''
        },
        {
          text: 'Grade',
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
