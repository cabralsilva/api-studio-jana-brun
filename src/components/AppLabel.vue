<template>
    <div class="" v-text="label"/>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'AppLabel',
  components: {
  },
  props: {
    label: String
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
    div {
        color: rgba(0, 0, 0, 0.38);
    }
</style>
