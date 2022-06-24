<template>
  <v-snackbar v-model="show" :timeout="timeout" :color="color">
    {{ text }}
    <template v-slot:action="{ attrs }">
      <v-btn color="blue" text v-bind="attrs" @click="show = false">
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script lang="ts">
import Vue from 'vue'
import EventBus from '@/store/enventBus'

export default Vue.extend({
  name: 'AppSnackbar',
  components: {
  },
  props: {
  },
  data () {
    return {
      show: false,
      text: '',
      color: 'primary',
      timeout: 2000
    }
  },
  methods: {
  },
  watch: {
  },
  created () {
    EventBus.onHttp4xx((response: any) => {
      this.show = true
      this.text = response.data.friendlyMessagesList.join(' | ')
      this.timeout = 2000
    })

    EventBus.onInvalidForm((alert: any) => {
      this.show = true
      this.text = alert
      this.timeout = 2000
    })
  }
})
</script>
