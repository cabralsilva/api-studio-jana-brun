<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formGrate" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="grate.code" :rules="[required]" label="Código"/>
              <v-select v-model="grate.typeOfValue" :rules="[requiredSelect]" :items="TypeOfValueList" item-text="value" item-value="key" label="Tipo de valor" />
            </v-col>
            <v-col>
              <v-text-field v-model="grate.description" :rules="[required]" label="Descrição"/>
              <v-select v-model="grate.status" :rules="[requiredSelect]" :items="statusActiveList" item-text="value" item-value="key" label="Status" />
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <select-multiple :callback="setItems" :payload="grate.itemList" />
            </v-col>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/register/product/grate')"> Cancelar </v-btn>
              <v-btn color="primary"  @click="save"> Salvar </v-btn>
            </v-col>
          </v-row>
        </v-form>
      </v-card>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'
import { StatusActiveList } from '@/model/StatusActiveEnum'
import { TypeOfValueList } from '@/model/TypeOfValueEnum'
import SelectMultiple from '@/components/SelectMultiple.vue'

export default Vue.extend({
  components: {
    SelectMultiple
  },
  data () {
    return {
      statusActiveList: StatusActiveList,
      TypeOfValueList: TypeOfValueList,
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      grate: {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        typeOfValue: null,
        status: null,
        itemList: []
      }
    }
  },
  watch: {
  },
  methods: {
    setItems (items: any) {
      this.grate.itemList = items
    },
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
    navigation (to: any) {
      // eslint-disable-next-line
      this.$router.push(to).catch(_err => { })
    },
    async save () {
      if ((this.$refs.formGrate as Vue & { validate: () => boolean }).validate()) {
        console.log('passou: ', this.grate)
        try {
          if (this.grate.identifier) {
            await httpAPI.put('/grate', this.grate)
          } else {
            await httpAPI.post('/grate', this.grate)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          this.navigation('/panel/register/product/grate')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    clear () {
      this.grate = {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        typeOfValue: null,
        status: null,
        itemList: []
      }
    },
    async getGrate () {
      if (this.grate.identifier) {
        const search = {
          columnList: [
            'identifier',
            'code',
            'description',
            'typeOfValue',
            'status',
            'itemList'
          ],
          resultUnique: true,
          example: {
            identifier: this.grate.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/grate/search', search)
        this.grate = response.data.data.result[0]
      }
    }
  },
  created () {
    if (this.grate.identifier) {
      this.getGrate()
    }
  }
})
</script>
