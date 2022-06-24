<template>
  <v-dialog v-model="show" width="500" persistent>
    <v-card>
      <v-card-title :class="'text-h5 ' + pType">
        <span style="color: white">{{ pTitle }}</span>
      </v-card-title>
      <v-card-text>
        <v-container v-html="pContent">
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="callback()">
          {{ pButtonText }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'AlertDialog',
  components: {
  },
  props: {
    pType: {
      type: String,
      default: 'secondary'
    },
    pShow: {
      type: Boolean,
      default: false
    },
    pTitle: {
      type: String,
      default: 'Selecione'
    },
    pContent: {
      type: String,
      required: true
    },
    pButtonText: {
      type: String,
      default: 'OK'
    },
    callback: {
      type: Function
    }
  },
  data () {
    return {
      show: this.pShow
    }
  },
  methods: {
  },
  watch: {
  }
})
</script>
