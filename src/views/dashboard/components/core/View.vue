<template>
  <v-main>
    <router-view />
    <!-- <dashboard-core-footer /> -->
  </v-main>
</template>

<script lang="ts">
import Vue from 'vue'
// import DashboardCoreFooter from '@/views/dashboard/components/core/Footer.vue'
export default Vue.extend({
  name: 'DashboardCoreView',

  components: {
    // DashboardCoreFooter
  }
})
</script>>
