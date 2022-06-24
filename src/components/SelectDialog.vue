<template>
  <v-dialog v-model="show">
    <v-card>
      <v-card-title class="text-h5 grey primary">
        <span style="color: white">{{ pTitle }}</span>
      </v-card-title>
      <v-card-text>
        <v-text-field
          v-model="options.searchText"
          label="Pesquisar"
          class="pa-3"
        ></v-text-field>
        <v-data-table class="row-pointer elevation-1"
          v-model="selected"
          dense
          item-key="identifier"
          :headers="headers"
          :items="payload.itemList"
          :options.sync="options"
          :server-items-length="payload.total"
          :loading="payload.loading"
          @click:row="rowClick">
        </v-data-table>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="select()">
          OK
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import Vue from 'vue'
import { HttpSearchRequest } from '@/model/HttpUtils'

export default Vue.extend({
  name: 'SelectDialog',
  components: {
  },
  props: {
    pShow: {
      type: Boolean,
      default: false
    },
    pHeaders: {
      type: [] as any,
      default: () => [
        {
          text: 'ID',
          align: 'start',
          sortable: false,
          value: 'identifier'
        },
        { text: 'Nome', value: 'name' }
      ]
    },
    pData: {
      type: Function,
      required: true
    },
    pOptions: {
      type: Object as any,
      default: () => ({
        searchText: '',
        sortBy: ['identifier'],
        sortDesc: [true],
        page: 1,
        itemsPerPage: 5
      })
    },
    pTitle: {
      type: String,
      default: 'Selecione'
    },
    onSelect: {
      type: Function,
      required: true
    }
  },
  data () {
    return {
      show: this.pShow,
      selected: [] as any,
      headers: this.pHeaders,
      options: this.pOptions,
      payload: {
        itemList: [] as any,
        total: null as any,
        loading: false
      }
    }
  },
  methods: {
    rowClick (item: any, row: any) {
      this.selected.push(item)
      this.select()
    },
    select () {
      this.onSelect(this.selected)
      this.show = false
    }
  },
  watch: {
    options: {
      async handler () {
        const search = {
          example: {},
          globalFilter: this.options.searchText,
          currentPage: this.options.page - 1,
          orderColumn: this.options.sortBy[0],
          asc: this.options.sortDesc[0],
          sizePage: this.options.itemsPerPage
        } as HttpSearchRequest
        this.payload.loading = true
        this.payload = await this.pData(search)
        console.log('PAYLOAD', this.payload)
      },
      deep: true
    }
  }
})
</script>

<style scoped>
.row-pointer >>> tbody tr :hover {
  cursor: pointer;
}
</style>
