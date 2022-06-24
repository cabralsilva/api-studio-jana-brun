<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formNotice" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="notice.title" :rules="[required]" label="Título"/>
              <v-text-field v-model="notice.level" :rules="[required, onlyNumber]" label="Prioridade"/>
              <input-date label="Data de início" :required="required" :callback="setStartDate" :initDate="notice.startDate"/>
            </v-col>
            <v-col>
              <v-select v-model="notice.status" :rules="[requiredSelect]" :items="statusActiveList" item-text="value" item-value="key" label="Status" />
              <v-select v-model="notice.type" :rules="[requiredSelect]" :items="typeOfNoticeList" item-text="value" item-value="key" label="Tipo" />
              <input-date label="Data de término" :callback="setEndDate" :initDate="notice.endDate"/>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-textarea label="Texto" :rules="[required]" v-model="notice.description" />
            </v-col>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/register/notice')"> Cancelar </v-btn>
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
import { StatusActiveList } from '@/model/StatusActiveEnum'
import { TypeOfNoticeList } from '@/model/TypeOfNoticeEnum'
import moment from 'moment'

export default Vue.extend({
  components: {
    InputDate
  },
  data () {
    return {
      statusActiveList: StatusActiveList,
      typeOfNoticeList: TypeOfNoticeList,
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      notice: {
        identifier: this.$route.params.identifier,
        title: null,
        description: null,
        type: null,
        level: null,
        status: null,
        startDate: null,
        endDate: null
      }
    }
  },
  watch: {
  },
  methods: {
    required (value: any) {
      return !!value || 'Required.'
    },
    onlyNumber (value: any) {
      if (value && value.length > 0) {
        const lastChar = value.charAt(value.length - 1)
        if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
          return 'Invalid format'
        }
      }
      return true
    },
    requiredSelect (value: any) {
      if (value) {
        if (value.identifier) {
          return true
        }

        if (value.length) {
          return true
        }
      }
      return 'Required.'
    },
    setStartDate (date: any) {
      this.notice.startDate = date
    },
    setEndDate (date: any) {
      this.notice.endDate = date
    },
    navigation (to: any) {
      // eslint-disable-next-line
      this.$router.push(to).catch(_err => { })
    },
    async save () {
      if ((this.$refs.formNotice as Vue & { validate: () => boolean }).validate()) {
        console.log('passou: ', this.notice)
        try {
          if (this.notice.identifier) {
            await httpAPI.put('/notice', this.notice)
          } else {
            await httpAPI.post('/notice', this.notice)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          this.navigation('/panel/register/notice')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    clear () {
      this.notice = {
        identifier: this.$route.params.identifier,
        title: null,
        description: null,
        type: null,
        level: null,
        status: null,
        startDate: null,
        endDate: null
      }
    },
    async getNotice () {
      if (this.notice.identifier) {
        const search = {
          columnList: [
            'identifier',
            'title',
            'description',
            'type',
            'level',
            'status',
            'startDate',
            'endDate'
          ],
          resultUnique: true,
          example: {
            identifier: this.notice.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/notice/search', search)
        this.notice = response.data.data.result[0]
        this.notice.startDate = moment(this.notice.startDate).format('YYYY-MM-DD') as any
        this.notice.endDate = moment(this.notice.endDate).format('YYYY-MM-DD') as any
      }
    }
  },
  created () {
    if (this.notice.identifier) {
      this.getNotice()
    }
  }
})
</script>
