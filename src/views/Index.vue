<template>
  <v-app>
    <dashboard-core-app-bar />

    <dashboard-core-drawer />

    <dashboard-core-view />

    <dashboard-core-settings />

    <app-snackbar/>
  </v-app>
</template>

<script lang="ts">
import DashboardCoreAppBar from '@/views/dashboard/components/core/AppBar.vue'
import DashboardCoreDrawer from '@/views/dashboard/components/core/Drawer.vue'
import DashboardCoreSettings from '@/views/dashboard/components/core/Settings.vue'
import DashboardCoreView from '@/views/dashboard/components/core/View.vue'
import AppSnackbar from '@/components/AppSnackbar.vue'

export default {
  name: 'DashboardIndex',
  components: {
    DashboardCoreAppBar,
    DashboardCoreDrawer,
    DashboardCoreSettings,
    DashboardCoreView,
    AppSnackbar
  },
  data () {
    return {
      expandOnHover: false
    }
  }
}
</script>
