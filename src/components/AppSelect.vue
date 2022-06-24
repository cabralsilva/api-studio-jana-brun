<template>
  <v-col>
    <v-select v-model="value" :items="itemList" :rules="[requiredSelect]"
      item-text="value" item-value="identitifer"
      attach chips :label="label" return-object
      @change="callback"/>
  </v-col>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'AppSelect',
  props: {
    asyncLoading: {
      type: Function
    },
    params: {
      type: [] as any
    },
    items: {
      type: []
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
  async created () {
    this.itemList = this.items
    if (this.asyncLoading) {
      this.itemList = await this.asyncLoading(this.params)
    }
  },
  data () {
    return {
      value: this.itemInitial,
      itemList: [] as any
    }
  },
  watch: {
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

<style scoped>
</style>
