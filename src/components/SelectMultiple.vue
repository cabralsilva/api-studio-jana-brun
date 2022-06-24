<template>
  <v-container fluid>
    <v-combobox v-model="model" :filter="filter" :hide-no-data="!search" :items="items"
      :search-input.sync="search" hide-selected label="Itens da grade" multiple small-chips solo>
      <template v-slot:no-data>
        <v-list-item>
          <span class="subheading">Add </span>
          <v-chip :color="`${colors[nonce - 1]} lighten-3`" label small>
            {{ search }}
          </v-chip>
        </v-list-item>
      </template>
      <template v-slot:selection="{ attrs, item, parent, selected }">
        <v-chip v-if="item === Object(item)" v-bind="attrs"
          :color="`${item.color} lighten-3`" :input-value="selected" label small>
          <span class="pr-2">
            {{ item.value }}
          </span>
          <v-icon small @click="parent.selectItem(item)">
            $delete
          </v-icon>
        </v-chip>
      </template>
      <template v-slot:item="{ index, item }">
        <v-text-field v-if="editing === item" v-model="editing.value" autofocus flat
          background-color="transparent" hide-details solo @keyup.enter="edit(index, item)"></v-text-field>
        <v-chip v-else :color="`${item.color} lighten-3`" dark label small>
          {{ item.text }}
        </v-chip>
        <v-spacer></v-spacer>
        <v-list-item-action @click.stop>
          <v-btn icon @click.stop.prevent="edit(index, item)">
            <v-icon>{{ editing !== item ? 'mdi-pencil' : 'mdi-check' }}</v-icon>
          </v-btn>
        </v-list-item-action>
      </template>
    </v-combobox>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  props: {
    callback: {
      type: Function,
      required: true
    },
    payload: {
      type: Array
    }
  },
  data () {
    return {
      activator: null,
      attach: null,
      colors: ['green', 'purple', 'indigo', 'cyan', 'teal', 'orange'],
      editing: null,
      editingIndex: -1,
      items: [
        { header: 'Insira um valor' }
      ],
      nonce: 1,
      menu: false,
      model: [],
      x: 0,
      search: null,
      y: 0
    }
  },
  watch: {
    payload (val, prev) {
      this.model = val.map((element: object) => ({
        ...element,
        color: 'gray'
      }))
    },
    model (val, prev) {
      if (val.length === prev.length) return

      this.model = val.map((v: any) => {
        if (typeof v === 'string') {
          v = {
            value: v,
            color: this.colors[this.nonce - 1]
          }
          this.items.push(v)
          this.nonce++
        }
        return v
      })
      this.callback(this.model)
    }
  },
  methods: {
    edit (index: any, item: any) {
      if (!this.editing) {
        this.editing = item
        this.editingIndex = index
      } else {
        this.editing = null
        this.editingIndex = -1
      }
    },
    filter (item: any, queryText: any, itemText: any) {
      if (item.header) return false

      const hasValue = (val: any) => val != null ? val : ''

      const text = hasValue(itemText)
      const query = hasValue(queryText)

      return text.toString()
        .toLowerCase()
        .indexOf(query.toString().toLowerCase()) > -1
    }
  }
})
</script>

<style scoped>
.label {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 15px 0px;
  font-size: 1.4rem;
}

.element {
  display: flex;
  align-self: stretch;
  margin: 10px 0px;
}

input,
textarea,
select {
  flex: 1;
  font-size: 1.4rem;
  outline: none;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 5px 10px;
}

input:focus {
  border: 1px solid #bbb;
}
</style>
