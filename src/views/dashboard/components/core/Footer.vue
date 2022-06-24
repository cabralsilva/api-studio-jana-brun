<template>
  <v-footer id="dashboard-core-footer" bottom absolute>
    <v-container>
      <v-row align="center" no-gutters>
        <v-spacer class="hidden-sm-and-down" />
        <v-col cols="12" md="auto">
          <div class="text-body-1 font-weight-light pt-6 pt-md-0 text-center">
            &copy; 2022
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-footer>
</template>

<script lang="ts">
import Vue from 'vue'
export default Vue.extend({
  name: 'DashboardCoreFooter',
  data () {
    return {
    }
  }
})
</script>

<style lang="sass">
  #dashboard-core-footer
    a
      font-size: .825rem
      font-weight: 500
      text-decoration: none
      text-transform: uppercase
</style>
