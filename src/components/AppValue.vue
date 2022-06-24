<template>
  <div v-if="typeof value == 'object'">
    <v-chip v-for="item of value" :key="item.identifier" class="ma-2">
      {{ item.description}}
    </v-chip>
  </div>
  <div v-else v-text="value || '-'"/>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'AppValue',
  components: {
  },
  props: {
    value: {
      type: [Number, Object, String, Array]
    }
  },
  data () {
    return {

    }
  }
})
</script>

<style scoped>
div {
  color: rgba(0, 0, 0, 0.87);
}
</style>
