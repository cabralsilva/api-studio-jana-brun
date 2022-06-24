<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formClassroom" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="classroom.description" :rules="[required]" label="Descrição"/>
            </v-col>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/register/classroom')"> Cancelar </v-btn>
              <v-btn color="primary"  @click="save"> Salvar </v-btn>
            </v-col>
          </v-row>
        </v-form>
      </v-card>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import InputDate from '@/components/InputDate.vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'

export default Vue.extend({
  components: {
  },
  data () {
    return {
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      classroom: {
        identifier: this.$route.params.identifier,
        description: null
      }
    }
  },
  watch: {
  },
  methods: {
    required (value: any) {
      return !!value || 'Required.'
    },
    navigation (to: any) {
      // eslint-disable-next-line
      this.$router.push(to).catch(_err => { })
    },
    async save () {
      if ((this.$refs.formClassroom as Vue & { validate: () => boolean }).validate()) {
        try {
          if (this.classroom.identifier) {
            await httpAPI.put('/classroom', this.classroom)
          } else {
            await httpAPI.post('/classroom', this.classroom)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          this.navigation('/panel/register/classroom')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    clear () {
      this.classroom = {
        identifier: this.$route.params.identifier,
        description: null
      }
    },
    async getClassroom () {
      if (this.classroom.identifier) {
        const search = {
          columnList: [
            'identifier',
            'description'
          ],
          resultUnique: true,
          example: {
            identifier: this.classroom.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/classroom/search', search)
        this.classroom = response.data.data.result[0]
      }
    }
  },
  created () {
    if (this.classroom.identifier) {
      this.getClassroom()
    }
  }
})
</script>
