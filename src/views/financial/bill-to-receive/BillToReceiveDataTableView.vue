<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="items"
      :options.sync="options"
      :server-items-length="total"
      :loading="loading"
      class="elevation-1"
    >
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small class="ma-2" @click="navigation('/panel/financial/bill-to-receive/edit/' + item.identifier)">mdi-pencil</v-icon>
        <v-icon small class="ma-2" @click="showDialogRemove(item.identifier)">mdi-delete</v-icon>
      </template>
    </v-data-table>
    <v-dialog
      v-model="dialog"
      max-width="290"
    >
      <v-card>
        <v-card-title class="text-h5">
          Tem certeza?
        </v-card-title>

        <v-card-text>
          Ao clicar em 'confirmar' esta ação se tornará irreversível. Deseja confirma?
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="default" text @click="dialog = false">
            Cancelar
          </v-btn>

          <v-btn color="primary" text @click="dialog = false">
            Confirmar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest, HttpSearchResponse } from '@/model/HttpUtils'
import { StatusOfBillEnum, StatusOfBillType } from '@/model/StatusOfBillEnum'
import moment from 'moment'

export default Vue.extend({
  name: 'BillToReceiveTableView',
  components: {},
  props: {},
  data () {
    return {
      dialog: false,
      total: 0,
      items: [] as any,
      loading: true,
      options: {
        searchText: '',
        sortBy: ['identifier'],
        sortDesc: [true],
        page: 1,
        itemsPerPage: 5
      },
      headers: [
        {
          text: 'ID',
          align: 'start',
          sortable: false,
          value: 'identifier'
        },
        { text: 'Título', value: 'title' },
        { text: 'Cliente', value: 'customer.nickName' },
        { text: 'Data de emissão', value: 'emissionDate' },
        { text: 'Status', value: 'status' },
        { text: 'Ações', value: 'actions', sortable: false }
      ]
    }
  },
  watch: {
    options: {
      handler () {
        this.getDataFromApi()
      },
      deep: true
    }
  },
  methods: {
    showDialogRemove (id: any) {
      this.dialog = true
    },
    navigation (to: any) {
      if (to) {
        // eslint-disable-next-line
        this.$router.push(to).catch(err => { })
      }
    },
    getDataFromApi () {
      const search = {
        example: {},
        globalFilter: this.options.searchText,
        currentPage: this.options.page - 1,
        orderColumn: this.options.sortBy[0],
        asc: this.options.sortDesc[0],
        sizePage: this.options.itemsPerPage
      } as HttpSearchRequest
      this.loading = true
      httpAPI.post('/bill-to-receive/search', search)
        .then((response) => {
          this.items = Object.keys(response.data.data.result).map(index => {
            const item = {
              identifier: response.data.data.result[index].identifier,
              title: response.data.data.result[index].title,
              customer: response.data.data.result[index].customer,
              emissionDate: moment(response.data.data.result[index].emissionDate).format('DD/MM/YYYY'),
              status: StatusOfBillEnum[response.data.data.result[index].status as StatusOfBillType]
            }

            return item
          })
          this.total = response.data.data.total
          this.loading = false
        })
    }
  }
})
</script>

<style scoped>
</style>
