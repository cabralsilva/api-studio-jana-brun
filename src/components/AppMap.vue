<template>
  <v-col class="py-1">
      <app-label :label="label" />
      <app-value :value="value" />
  </v-col>
</template>

<script lang="ts">
import Vue from 'vue'
import AppLabel from '@/components/AppLabel.vue'
import AppValue from '@/components/AppValue.vue'

export default Vue.extend({
  name: 'AppData',
  components: {
    AppLabel,
    AppValue
  },
  props: {
    label: String,
    value: [Object, String, Number, Array]
  },
  methods: {

  },
  data () {
    return {

    }
  }
})
</script>

<style scoped>

</style>
