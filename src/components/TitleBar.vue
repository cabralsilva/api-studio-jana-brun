<template>
  <v-app-bar app clipped-left dark color="primary">
    <v-app-bar-nav-icon @click.stop="toggleMenuSide"></v-app-bar-nav-icon>
    <v-toolbar-title>Painel administrativo | Studio Jana Brun</v-toolbar-title>
  </v-app-bar>
</template>

<script lang="ts">
import Vue from 'vue'
import EventBus from '@/store/enventBus'

export default Vue.extend({
  name: 'TitleBar',
  components: {
  },
  props: {
    initialMini: Boolean
  },
  methods: {
    toggleMenuSide () {
      EventBus.toggleMenuSide(this.mini)
    }
  },
  created () {
    EventBus.onToggleMenuSide((prop: any) => {
      this.mini = prop
    })
  },
  data () {
    return {
      mini: this.initialMini
    }
  }
})
</script>
