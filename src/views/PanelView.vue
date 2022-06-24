<template>
  <v-main>
    <router-view />
    <!-- <dashboard-core-footer /> -->
  </v-main>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'PanelView',
  components: {
  },
  data () {
    return {
    }
  },
  methods: {

  },
  created () {
    console.log(this.$store.state.userDetails)
  }
})
</script>

<style scoped>
</style>
