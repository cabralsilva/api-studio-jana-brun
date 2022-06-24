<template>
  <v-dialog v-model="show"
      max-width="600px">
    <v-card>
      <v-card-title class="text-h5 grey primary">
        <span style="color: white">{{ pTitle }}</span>
      </v-card-title>
      <v-card-text>
        <v-form ref="formDialog" lazy-validation>
          <slot name="inputForm"></slot>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn color="blue darken-1" text @click.prevent="save()">
          Salvar
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'FormDialog',
  components: {
  },
  props: {
    pShow: {
      type: Boolean,
      default: false
    },
    pTitle: {
      type: String,
      default: 'Formulário'
    },
    callback: {
      type: Function,
      required: true
    }
  },
  data () {
    return {
      show: this.pShow
    }
  },
  methods: {
    save () {
      for (const form of Object.keys(this.$refs)) {
        if (!(this.$refs[form] as Vue & { validate: () => boolean }).validate()) {
          return
        }
      }
      this.callback()
        .then((_success: any) => {
          this.show = false
        })
        .catch((_error: any) => {
          this.show = true
        })
    }
  },
  watch: {
  }
})
</script>

<style scoped>
</style>
