<template>
    <div>
      <v-row>
        <v-col align="right">
          <v-btn @click="navigation('/panel/register/class/edit')" icon middle>
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-col>
      </v-row>
      <v-breadcrumbs :items="breadcrumbs">
        <template v-slot:divider>
          <v-icon>mdi-chevron-right</v-icon>
        </template>
      </v-breadcrumbs>
      <v-card>
        <v-card-text>
          <class-data-table-view></class-data-table-view>
        </v-card-text>
      </v-card>
    </div>
</template>

<script lang="ts">
import Vue from 'vue'
import ClassDataTableView from '@/views/class/ClassDataTableView.vue'

export default Vue.extend({
  components: {
    ClassDataTableView
  },
  data () {
    return {
      breadcrumbs: [
        {
          text: 'Home',
          disabled: false,
          href: '/panel'
        },
        {
          text: 'Cadastro',
          disabled: true,
          href: ''
        },
        {
          text: 'Turmas',
          disabled: true,
          href: ''
        }
      ],
      classList: []
    }
  },
  methods: {
    navigation (to: any) {
      if (to) {
        // eslint-disable-next-line
        this.$router.push(to).catch(err => { })
      }
    }
  }
})
</script>
