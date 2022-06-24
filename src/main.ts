import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store/store'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false
Vue.prototype.$hashCode = () => {
  let hash = 0
  for (let i = 0; i < 5; i++) {
    hash = ((hash << 5) - hash) + (Math.random() * 100)
    hash = hash & hash // Convert to 32bit integer
  }
  return hash
}

Vue.prototype.$requiredObject = (value: any) => {
  if (value) {
    if (value.identifier) {
      return true
    }

    if (value.length) {
      return true
    }
  }
  return 'Obrigatório'
}

Vue.prototype.$requiredIntegerNumber = (value: any) => {
  if (value && value.length > 0) {
    const lastChar = value.charAt(value.length - 1)
    if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
      return 'Invalid format'
    }
  }
  return true
}

Vue.prototype.$getValueFinancial = (value: any): number => {
  return Number(Number.parseFloat(value).toFixed(2))
}

Vue.mixin({
  methods: {
    getValueFinancial: (value: any): number => {
      return Number(Number.parseFloat(value).toFixed(2))
    }
  }
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
