<template>
    <div>
      <v-card class="pa-2">
        <v-form ref="formClass" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field v-model="clazz.code" :rules="[]" label="Code"/>
            </v-col>
            <v-col>
              <v-text-field v-model="clazz.description" :rules="[required]" label="Descrição"/>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <input-date label="Data de início" :callback="(date) => this.clazz.startDate = date" :initDate="this.clazz.startDate"/>
            </v-col>
            <v-col>
              <input-date label="Data de término" :callback="(date) => this.clazz.endDate = date" :initDate="this.clazz.endDate"/>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-select label="Produto vinculado" v-model="clazz.product" :items="this.productList"
                        item-text="description" item-value="identitifer"
                        attach chips return-object
                        @change="selectProduct(clazz.product)"/>
            </v-col>
            <v-col>
              <v-text-field v-model="clazz.inviteWhatsAppGroup" :rules="[required]" label="Link do  grupo WhatsApp"/>
            </v-col>
          </v-row>
          <v-row class="elevation-6 ma-0">
            <v-col>
              <v-form ref="formSchedule" lazy-validation>
                <v-row>
                  <v-col>
                    <app-label label="Dias e horários" />
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="col-3">
                    <v-select
                      :rules="[requiredSelect]"
                      @change="() => { scheduleDetailClassEdit.oftenDay = null }"
                      v-model="scheduleDetailClassEdit.often"
                      :items="oftenList" item-text="value"
                      item-value="key"
                      label="Frequência" />
                  </v-col>
                  <v-col v-if="!disabledOftenDay(scheduleDetailClassEdit.often)">
                    <v-select v-if="isOftenDayList(scheduleDetailClassEdit.often)"
                      :disabled="!scheduleDetailClassEdit.often"
                      :rules="[requiredSelect]"
                      v-model="scheduleDetailClassEdit.oftenDay"
                      :items="getOftenDayList()"
                      item-text="value" item-value="key"
                      label="Dia" />
                    <input-date v-if="isOftenDayDate(scheduleDetailClassEdit.often)"
                      :required="required"
                      label="Dia"
                      :callback="(date) => { scheduleDetailClassEdit.oftenDay = date }"
                      :initDate="scheduleDetailClassEdit.oftenDay"
                      :startIn="'DATE'"/>
                  </v-col>
                  <v-col>
                    <input-time
                      :required="required"
                      :callback="(time) => scheduleDetailClassEdit.initTime = time"
                      :initTime="scheduleDetailClassEdit.initTime" label="Hora de início" />
                  </v-col>
                  <v-col>
                    <input-time
                      :required="required"
                      :callback="(time) => scheduleDetailClassEdit.endTime = time"
                      :endTime="scheduleDetailClassEdit.endTime" label="Hora de término" />
                  </v-col>
                  <v-col>
                    <v-select
                      :rules="[requiredSelect]"
                      v-model="scheduleDetailClassEdit.classroom" :items="classroomList"
                      item-text="description" item-value="identitifer"
                      attach chips label="Selecione a sala" return-object
                      @change="selectClassroom(scheduleDetailClassEdit.classroom)"/>
                  </v-col>
                  <v-col align="right">
                    <v-btn align="right" :disabled="false" color="primary" @click="addScheduleDetailClass(scheduleDetailClassEdit)"> Add </v-btn>
                  </v-col>
                </v-row>
              </v-form>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left">
                        Frequência
                      </th>
                      <th class="text-left">
                        Dia
                      </th>
                      <th class="text-left">
                        Horário
                      </th>
                      <th class="text-left">
                        Sala
                      </th>
                      <th class="text-left">
                        Ação
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(scheduleDetailClass, index) in clazz.scheduleDetailClassList" :key="scheduleDetailClass.identifier">
                      <td>
                        {{ oftenEnum[scheduleDetailClass.often] }}<br/>
                      </td>
                      <td>
                        {{ isOftenDayDate(scheduleDetailClass.often) ? getDateFormat(scheduleDetailClass.oftenDay) : scheduleDetailClass.oftenDay  }}
                      </td>
                      <td>
                        {{ `De ${scheduleDetailClass.initTime} às ${scheduleDetailClass.endTime}` }}
                      </td>
                      <td>
                        {{ scheduleDetailClass.classroom.description  }}
                      </td>
                      <td><v-icon @click="removeScheduleDetailClass(index)">mdi-delete-forever</v-icon></td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </v-col>
          </v-row>
          <v-divider class="py-6"></v-divider>
          <v-row class="elevation-6 ma-0">
            <v-form ref="formRole" lazy-validation>
              <v-col>
                <v-row>
                  <v-col>
                    <app-label label="Novas regras de pagamentos" />
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="col-3">
                    <v-select v-model="rolePaymentEdit.employee" :items="teacherList" clearable
                      item-text="person.name" item-value="identitifer"
                      attach chips label="Selecione o professor(a)" return-object
                      @change="selectTeacher(rolePaymentEdit.employee)"/>
                  </v-col>
                  <v-col>
                    <v-text-field :rules="[number]" v-model="rolePaymentEdit.sinceStudentNumber" label="Mínimo de alunos" />
                  </v-col>
                  <v-col>
                    <v-text-field :rules="[number]" v-model="rolePaymentEdit.untilStudentNumber" label="Máximo de alunos" />
                  </v-col>
                  <v-col>
                    <v-select :rules="[requiredSelect]" v-model="rolePaymentEdit.typeOfPayment" :items="typeOfPaymentList" item-text="value" item-value="key" label="Tipo de pagamento" />
                  </v-col>
                  <v-col>
                    <v-text-field :rules="[required, number]" v-model="rolePaymentEdit.paymentValue" label="Valor" />
                  </v-col>
                  <v-col align="right">
                    <v-btn :disabled="false" color="primary" @click="addRole(rolePaymentEdit)"> Add </v-btn>
                  </v-col>
                </v-row>
              </v-col>
            </v-form>
          </v-row>
          <v-row>
            <v-col>
              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left">
                        Professor
                      </th>
                      <th class="text-left">
                        Regra
                      </th>
                      <th class="text-left">
                        Valor
                      </th>
                      <th class="text-left">
                        Ação
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(role, index) in clazz.rolePaymentList" :key="role.identifier">
                      <td>
                        <b>{{ role.employee.person.name }}</b><br/>
                      </td>
                      <td>
                        {{ getRoleDescription(role)  }}
                      </td>
                      <td>{{ getValuePaymentDescription(role) }}</td>
                      <td><v-icon @click="removeRole(index)">mdi-delete-forever</v-icon></td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </v-col>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn text @click="navigation('/panel/register/class')"> Cancelar </v-btn>
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
import InputTime from '@/components/InputTime.vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'
import { TypeOfSalaryList, TypeOfSalaryEnum } from '@/model/TypeOfSalaryEnum'
import { OftenList, OftenEnum, OftenType } from '@/model/OftenEnum'
import { DayOfWeekList, DayOfWeekEnum } from '@/model/DayOfWeekEnum'
import AppLabel from '@/components/AppLabel.vue'
import moment from 'moment'

export default Vue.extend({
  components: {
    AppLabel,
    InputDate,
    InputTime
  },
  data () {
    return {
      typeOfPaymentList: TypeOfSalaryList,
      dayOfWeekList: DayOfWeekList,
      oftenList: OftenList,
      oftenEnum: OftenEnum,
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      clazz: {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        product: {} as any,
        startDate: null,
        endDate: null,
        inviteWhatsAppGroup: null,
        rolePaymentList: [] as any,
        scheduleDetailClassList: [] as any
      },
      productList: [],
      teacherList: [],
      classroomList: [],
      rolePaymentEdit: {
        employee: {
          identifier: null as any
        },
        sinceStudentNumber: 1 as any,
        untilStudentNumber: 2 as number,
        typeOfPayment: null as any,
        paymentValue: null as any
      },
      scheduleDetailClassEdit: {
        identifier: null as any,
        often: null as any,
        oftenDay: null as any,
        initTime: null as any,
        endTime: null as any,
        classroom: null as any
      }
    }
  },
  watch: {
  },
  methods: {
    getDateFormat (value: any, format = 'DD/MM/YYYY') {
      return moment(value).format(format) as any
    },
    getOftenDayList () {
      if (this.scheduleDetailClassEdit.often === Object.keys(OftenEnum)[Object.values(OftenEnum).indexOf(OftenEnum.WEEKLY as unknown as OftenEnum)]) {
        return this.dayOfWeekList
      }
      if (this.scheduleDetailClassEdit.often === Object.keys(OftenEnum)[Object.values(OftenEnum).indexOf(OftenEnum.MONTHLY as unknown as OftenEnum)]) {
        const arraySingle = Array.from({ length: (32 - 1) }, (v, k) => k + 1)
        return arraySingle.map((day: any) => { return { key: day, value: day } })
      }

      return this.dayOfWeekList
    },
    disabledOftenDay (oftenDay: any) {
      if (!oftenDay) {
        return true
      }

      if (oftenDay === Object.keys(OftenEnum)[Object.values(OftenEnum).indexOf(OftenEnum.DAILY as unknown as OftenEnum)]) {
        return true
      }

      return false
    },
    isOftenDayDate (oftenDay: any) {
      if (this.disabledOftenDay(oftenDay)) {
        return false
      }
      if (oftenDay === Object.keys(OftenEnum)[Object.values(OftenEnum).indexOf(OftenEnum.ONCE as unknown as OftenEnum)]) {
        return true
      }
      if (oftenDay === Object.keys(OftenEnum)[Object.values(OftenEnum).indexOf(OftenEnum.YEARLY as unknown as OftenEnum)]) {
        return true
      }

      return false
    },
    isOftenDayList (oftenDay: any) {
      if (this.disabledOftenDay(oftenDay)) {
        return false
      }
      if (oftenDay === Object.keys(OftenEnum)[Object.values(OftenEnum).indexOf(OftenEnum.WEEKLY as unknown as OftenEnum)]) {
        return true
      }
      if (oftenDay === Object.keys(OftenEnum)[Object.values(OftenEnum).indexOf(OftenEnum.MONTHLY as unknown as OftenEnum)]) {
        return true
      }

      return false
    },
    getRoleDescription (role: any) {
      let roleDescription = 'Sem regras'
      if (role.sinceStudentNumber) {
        if (role.untilStudentNumber) {
          roleDescription = `De ${role.sinceStudentNumber} até ${role.untilStudentNumber} alunos(as)`
        } else {
          roleDescription = `À partir de ${role.sinceStudentNumber} alunos(as)`
        }
      } else {
        if (role.untilStudentNumber) {
          roleDescription = `Até ${role.untilStudentNumber} alunos(as)`
        }
      }

      return roleDescription
    },
    getValuePaymentDescription (role: any) {
      if (role.typeOfPayment === Object.keys(TypeOfSalaryEnum)[Object.values(TypeOfSalaryEnum).indexOf(TypeOfSalaryEnum.BY_PERCENT as unknown as TypeOfSalaryEnum)]) {
        return `${role.paymentValue}% das matrículas`
      }

      if (role.typeOfPayment === Object.keys(TypeOfSalaryEnum)[Object.values(TypeOfSalaryEnum).indexOf(TypeOfSalaryEnum.BY_HOUR as unknown as TypeOfSalaryEnum)]) {
        return `R$ ${role.paymentValue}/hora`
      }

      if (role.typeOfPayment === Object.keys(TypeOfSalaryEnum)[Object.values(TypeOfSalaryEnum).indexOf(TypeOfSalaryEnum.BY_MONTH as unknown as TypeOfSalaryEnum)]) {
        return `R$ ${role.paymentValue}/mês`
      }

      return `R$ ${role.paymentValue}`
    },
    addRole (role: any) {
      if (!(this.$refs.formRole as Vue & { validate: () => boolean }).validate()) {
        this.snackbar.show = true
        this.snackbar.text = 'Preencha todos os campos obrigatórios'
        this.snackbar.timeout = 0
        return
      }
      this.clazz.rolePaymentList.push(role)
      this.rolePaymentEdit = {
        employee: {
          identifier: null as any
        },
        sinceStudentNumber: +role.untilStudentNumber + 1,
        untilStudentNumber: +role.untilStudentNumber + 2,
        typeOfPayment: null as any,
        paymentValue: null as any
      }
    },
    addScheduleDetailClass (scheduleDetailClass: any) {
      if (!(this.$refs.formSchedule as Vue & { validate: () => boolean }).validate()) {
        return
      }
      this.clazz.scheduleDetailClassList.push(scheduleDetailClass)
    },
    removeRole (index: any) {
      this.clazz.rolePaymentList.splice(index, 1)
    },
    removeScheduleDetailClass (index: any) {
      this.clazz.scheduleDetailClassList.splice(index, 1)
    },
    required (value: any) {
      return !!value || 'Required.'
    },
    number (value: any) {
      if (value && value.length > 0) {
        const lastChar = value.charAt(value.length - 1)
        if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
          this.rolePaymentEdit.paymentValue = value.slice(0, -1)
        }
        const valueSplit = value.split('.')
        if (valueSplit.length > 2) {
          valueSplit.splice(2)
          this.rolePaymentEdit.paymentValue = valueSplit.join('.')
          value = this.rolePaymentEdit.paymentValue
        }

        if (valueSplit[1] && valueSplit[1].length > 2) {
          valueSplit[1] = valueSplit[1].substring(0, 2)
          this.rolePaymentEdit.paymentValue = valueSplit.join('.')
          value = this.rolePaymentEdit.paymentValue
        }

        if (!value.match(/^\d{0,5}(?:\.\d{1,2})?$/)) {
          return 'Invalid format'
        }
      }
      return true
    },
    async selectProduct (product: any) {
      this.clazz.product = {
        identifier: null as any
      }
      setTimeout(() => {
        this.clazz.product = product
      }, 10)
    },
    navigation (to: any) {
      // eslint-disable-next-line
      this.$router.push(to).catch(_err => { })
    },
    async save () {
      if ((this.$refs.formClass as Vue & { validate: () => boolean }).validate()) {
        try {
          if (this.clazz.identifier) {
            await httpAPI.put('/class', this.clazz)
          } else {
            await httpAPI.post('/class', this.clazz)
          }

          this.snackbar.show = true
          this.snackbar.text = 'Dados salvos com sucesso'
          this.snackbar.color = 'success'
          this.navigation('/panel/register/class')
        } catch (error) {
          this.snackbar.text = 'Erro ao processar: ' + error
          this.snackbar.show = true
        }
      }
    },
    clear () {
      this.clazz = {
        identifier: this.$route.params.identifier,
        code: null,
        description: null,
        product: {} as any,
        startDate: null,
        endDate: null,
        inviteWhatsAppGroup: null,
        rolePaymentList: [] as any,
        scheduleDetailClassList: [] as any
      }
    },
    requiredSelect (value: any) {
      if (value) {
        if (value.identifier) {
          return true
        }

        if (value.length) {
          return true
        }

        if (value !== undefined) {
          return true
        }
      }
      return 'Required.'
    },
    async selectTeacher (teacher: any) {
      this.rolePaymentEdit.employee = {
        identifier: null as any
      }
      setTimeout(() => {
        this.rolePaymentEdit.employee = teacher
      }, 10)
    },
    async selectClassroom (classroom: any) {
      this.scheduleDetailClassEdit.classroom = {
        identifier: null as any
      }
      setTimeout(() => {
        this.scheduleDetailClassEdit.classroom = classroom
      }, 10)
    },
    async getClass () {
      if (this.clazz.identifier) {
        const search = {
          resultUnique: true,
          example: {
            identifier: this.clazz.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/class/search', search)
        this.clazz = response.data.data.result[0]
        this.clazz.startDate = moment(this.clazz.startDate).format('YYYY-MM-DD') as any
        this.clazz.endDate = moment(this.clazz.endDate).format('YYYY-MM-DD') as any
      }
    },
    async getProductList () {
      const search = {
        example: {
          category: 'LESSON'
        },
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/product/search', search)
      this.productList = response.data.data.result
    },
    async getTeacherList () {
      const search = {
        example: {
          job: 'TEACHER'
        },
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/employee/search', search)
      this.teacherList = response.data.data.result
    },
    async getClassroomList () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/classroom/search', search)
      this.classroomList = response.data.data.result
    }
  },
  created () {
    this.getProductList()
    this.getTeacherList()
    this.getClassroomList()
    if (this.clazz.identifier) {
      this.getClass()
    }
  }
})
</script>
