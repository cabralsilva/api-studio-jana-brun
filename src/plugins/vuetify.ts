import Vue from 'vue'
import Vuetify from 'vuetify/lib/framework'

Vue.use(Vuetify)

const theme = {
  primary: '#e8048b',
  secondary: '#080787',
  accent: '#9C27b0',
  info: '#00CAE3'
}

export default new Vuetify({
  theme: {
    themes: {
      dark: theme,
      light: theme
    }
  }
})
