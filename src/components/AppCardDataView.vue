<template>
  <v-col :class="`col-xl-${columnSize} col-lg-${columnSize} col-md-12 col-sm-12 col-xs-12`">
    <v-card outlined dense >
      <slot name="title"></slot>
      <slot name="content"></slot>
    </v-card>
  </v-col>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'AppValue',
  components: {
  },
  props: {
    columnSize: {
      type: Number,
      default: 4
    }
  },
  methods: {

  },
  data () {
    return {

    }
  }
})
</script>

<style scoped>

</style>
