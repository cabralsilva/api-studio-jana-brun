<template>
  <v-app>
    <panel-menu-side />
    <panel-title-bar />

    <!-- Sizes your content based upon application components -->
    <v-main  class="pa-0 pa-sm-0 pa-xs-0 pa-md-0 pa-lg-0 pa-xl-0">
      <!-- Provides the application the proper gutter -->
      <div class="bg"></div>
      <v-container fluid>
        <!-- If using vue-router -->
        <router-view></router-view>
      </v-container>
    </v-main>
    <v-footer app>
      <!-- -->
    </v-footer>

  </v-app>
</template>

<script lang="ts">
import Vue from 'vue'
import PanelMenuSide from '@/components/MenuSide.vue'
import PanelTitleBar from '@/components/TitleBar.vue'

export default Vue.extend({
  name: 'Panel2View',
  components: {
    PanelMenuSide,
    PanelTitleBar
  },
  data () {
    return {
      screen: 'Panel2View'
    }
  },
  methods: {

  }
})
</script>

<style scoped>
.bg {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-image: url('@/assets/logo-light.jpg') ;
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  opacity: .1;
  width: 100%;
  height: 100%;
}
</style>
