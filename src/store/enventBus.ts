import Vue from 'vue'

export default new Vue({
  methods: {
    navigation (to: any) {
      this.$emit('onNavigation', to)
    },
    onNavigation (callback: any) {
      this.$on('onNavigation', callback)
    },
    toggleMenuSide (prop: any) {
      prop = !prop
      this.$emit('onToggleMenuSide', prop)
    },
    onToggleMenuSide (callback: any) {
      this.$on('onToggleMenuSide', callback)
    },
    http4xx (response: any) {
      this.$emit('onHttp4xx', response)
    },
    onHttp4xx (callback: any) {
      this.$on('onHttp4xx', callback)
    },
    invalidForm (alert: any) {
      console.log('trigger invalidForm')
      this.$emit('onInvalidForm', alert)
    },
    onInvalidForm (callback: any) {
      this.$on('onInvalidForm', callback)
    }
  }
})
