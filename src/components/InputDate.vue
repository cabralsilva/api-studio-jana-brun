<template>
  <div>
    <v-menu ref="menu" v-model="menu" :close-on-content-click="false" transition="scale-transition" offset-y min-width="auto">
      <template v-slot:activator="{ on, attrs }">
        <v-text-field
          v-model="date"
          :label="label"
          prepend-icon="mdi-calendar"
          readonly
          v-bind="attrs"
          v-on="on"
          :rules="[required]"
          :disabled="disabled"
        />
      </template>
      <v-date-picker v-model="date" :active-picker.sync="activePicker" :max="max" min="1950-01-01" @change="save"
      />
    </v-menu>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  props: {
    label: {
      type: String,
      default: 'Data'
    },
    disabled: {
      type: Boolean,
      default: false
    },
    callback: {
      type: Function,
      required: true
    },
    initDate: {
      type: String
    },
    required: {
      type: Function,
      default: () => { return true }
    },
    max: {
      type: String
    },
    startIn: {
      default: 'YEAR'
    }
  },
  data () {
    return {
      activePicker: '',
      date: this.initDate,
      menu: false
    }
  },
  watch: {
    initDate (val) {
      this.date = val
    },
    menu (val) {
      val && setTimeout(() => (this.activePicker = this.startIn))
    }
  },
  methods: {
    // required (value: any) {
    //   return !!value || 'Required.'
    // },
    save (date: any) {
      this.menu = false
      this.callback(date)
    }
  }
})
</script>

<style scoped>
.label {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 15px 0px;
  font-size: 1.4rem;
}

.element {
  display: flex;
  align-self: stretch;
  margin: 10px 0px;
}

input,
textarea,
select {
  flex: 1;
  font-size: 1.4rem;
  outline: none;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 5px 10px;
}

input:focus {
  border: 1px solid #bbb;
}
</style>
