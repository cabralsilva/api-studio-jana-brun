<template>
  <v-col>
    <v-select v-model="value" :items="itemList" :rules="validations"
      item-text="value" item-value="identitifer"
      attach chips :label="label" return-object
      multiple
      @change="callback"/>
  </v-col>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'AppSelectMultiple',
  props: {
    validations: {
      type: [],
      default: () => []
    },
    asyncLoading: {
      type: Function
    },
    params: {
      type: [] as any
    },
    items: {
      type: [] as any
    },
    label: {
      type: String
    },
    callback: {
      type: Function
    },
    itemInitial: {
      type: Object
    }
  },
  data () {
    return {
      value: this.itemInitial,
      itemList: [] as any
    }
  },
  async created () {
    this.itemList = this.items
    if (this.asyncLoading) {
      this.itemList = await this.asyncLoading(this.params)
    }
  },
  watch: {
    items (v1: any, v2: any) {
      console.log(v1, v2)
    }
  },
  methods: {
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
    }
  }
})
</script>

<style>
</style>
