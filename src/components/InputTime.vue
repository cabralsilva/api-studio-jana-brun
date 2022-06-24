<template>
  <v-menu
    ref="menu"
    v-model="selectTimePicker"
    :close-on-content-click="false"
    :nudge-right="40"
    :return-value.sync="time"
    transition="scale-transition"
    offset-y
    max-width="290px"
    min-width="290px"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-text-field
        v-model="time"
        :label="label"
        prepend-icon="mdi-clock-time-four-outline"
        readonly
        v-bind="attrs"
        v-on="on"
        :rules="[required]"
      ></v-text-field>
    </template>
    <v-time-picker
      v-if="selectTimePicker"
      v-model="time"
      full-width
      format="24hr"
      @click:minute="$refs.menu.save(time)"
      @change="save(time)"
    ></v-time-picker>
  </v-menu>
  <!-- <div>
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
        />
      </template>
      <v-date-picker v-model="date" :active-picker.sync="activePicker" :max="max" min="1950-01-01" @change="save"
      />
    </v-menu>
  </div> -->
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  props: {
    label: {
      type: String,
      default: 'Data'
    },
    callback: {
      type: Function,
      required: true
    },
    initTime: {
      type: String
    },
    required: {
      type: Function,
      default: () => { return true }
    },
    max: {
      type: String
    }
  },
  data () {
    return {
      time: this.initTime,
      selectTimePicker: false
    }
  },
  watch: {
    initTime (val) {
      this.time = val
    }
  },
  methods: {
    save (time: any) {
      this.selectTimePicker = false
      this.callback(time)
    }
  }
})
</script>

<style scoped>
</style>
