<template>
    <div>
      <v-row>
        <v-col align="right">
          <v-btn @click="navigation('/panel/register/person/employee/edit')" icon middle>
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
          <app-employee-data-table-view></app-employee-data-table-view>
        </v-card-text>
      </v-card>
    </div>
</template>

<script lang="ts">
import Vue from 'vue'
import AppEmployeeDataTableView from '@/views/employee/EmployeeDataTableView.vue'
import httpAxios from '@/plugins/axios'

export default Vue.extend({
  components: {
    AppEmployeeDataTableView
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
          text: 'Funcionarios',
          disabled: true,
          href: ''
        }
      ],
      employeeList: []
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
