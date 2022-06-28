<template>
  <div>
    <v-stepper v-model="currentStep">
      <v-stepper-header>
        <v-stepper-step :complete="currentStep == 1" step="1" editable :rules="[() => validSteps[0]]">
          Informações pessoais
        </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="currentStep == 2" step="2" editable :rules="[() => validSteps[1]]">
          Endereço
        </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="currentStep == 3" step="3" editable :rules="[() => validSteps[2]]">
          Contato
        </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="currentStep == 4" step="4" editable :rules="[() => validSteps[3]]">
          Vínculo
        </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="currentStep == 5" step="5" editable>
          Resumo
        </v-stepper-step>
      </v-stepper-header>
      <v-stepper-items>
        <v-stepper-content step="1">
          <v-form ref="formStepper1" v-model="validSteps[0]" lazy-validation>
            <v-row>
              <v-col>
                <v-text-field v-model="employee.person.name" :rules="[required]" label="Name"/>
                <v-text-field v-model="employee.person.documentNumber1" :rules="[required, onlyNumber]" label="CPF"/>
                <input-date label="Data de nascimento" :callback="setBornDate" :initDate="employee.person.bornDate"/>
                <div class="v-input theme--light v-text-field v-text-field--is-booted">
                  <v-checkbox class="pr-4" v-model="employee.medicinContinuous" label="Remédio contínuo?"/>
                  <v-text-field v-model="employee.medicinNotes" label="Quais"/>
                </div>
              </v-col>
              <v-col>
                <v-text-field v-model="employee.person.nickName" label="Apelido"/>
                <v-text-field v-model="employee.person.documentNumber2" label="Documento 2"/>
                <v-radio-group v-model="employee.person.genre" row label="Genêro" :rules="[required]">
                  <v-radio v-for="item in genreList" :key="item.key" :label="item.value" :value="item.key" />
                </v-radio-group>
                <div class="v-input theme--light v-text-field v-text-field--is-booted">
                  <v-checkbox class="pr-4" v-model="employee.allergiesContinuous" label="Alergias?"/>
                  <v-text-field v-model="employee.allergiesNotes" label="Quais"/>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn color="primary" class="mr-4"  @click="currentStep++"> Próximo </v-btn>
                <v-btn text @click="currentStep = 5"> Finalizar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-stepper-content>
        <v-stepper-content step="2">
          <v-form ref="formStepper2" v-model="validSteps[1]" lazy-validation>
            <v-row>
              <v-col class="col-2">
                <v-text-field v-model="employee.person.address.zipCode" label="CEP" :rules="[required]"/>
              </v-col>
              <v-col>
                <v-text-field v-model="employee.person.address.street" label="Avenida/Rua" :rules="[required]"/>
              </v-col>
              <v-col class="col-2">
                <v-text-field v-model="employee.person.address.number" label="Número" :rules="[required]"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field v-model="employee.person.address.complement" label="Complemento"/>
              </v-col>
              <v-col>
                <v-text-field v-model="employee.person.address.neighborhood.name" label="Bairro" :rules="[required]"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-select :rules="[requiredSelect]" v-model="employee.person.address.neighborhood.city" :items="cities" item-text="name" item-value="identifier" return-object label="Cidade" />
              </v-col>
              <v-col>
                <v-select :rules="[requiredSelect]" v-model="employee.person.address.neighborhood.city.state" :items="states" item-text="name" item-value="identifier" return-object label="Estado" />
              </v-col>
              <v-col>
                <v-select :rules="[requiredSelect]" v-model="employee.person.address.neighborhood.city.state.country" :items="countries" item-text="name" item-value="identifier" return-object label="País" />
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn text @click="currentStep--"> Anterior </v-btn>
                <v-btn color="primary" class="mr-4"  @click="currentStep++"> Próximo </v-btn>
                <v-btn text @click="currentStep = 5"> Finalizar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-stepper-content>
        <v-stepper-content step="3">
          <v-form ref="formStepper3" v-model="validSteps[2]" lazy-validation>
            <v-row>
              <v-col>
                <v-text-field v-model="employee.email" label="Email" :rules="[required, email]" />
              </v-col>
              <v-col>
                <v-text-field v-model="employee.phone1" label="Telefone principal" :rules="[required]"/>
              </v-col>
              <v-col>
                <v-text-field v-model="employee.phone2" label="Telefone secundário"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field v-model="employee.facebook" label="Facebook"/>
              </v-col>
              <v-col>
                <v-text-field v-model="employee.instagram" label="Instagram"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn text @click="currentStep--"> Anterior </v-btn>
                <v-btn color="primary" class="mr-4"  @click="currentStep++"> Próximo </v-btn>
                <v-btn text @click="currentStep = 5"> Finalizar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-stepper-content>
        <v-stepper-content step="4">
          <v-form ref="formStepper4" v-model="validSteps[3]" lazy-validation>
            <v-row>
              <v-col>
                <v-select label="Status"  :rules="[requiredSelect]" v-model="employee.status" :items="employeeStatusList" item-text="value" item-value="key"/>
              </v-col>
              <v-col>
                <v-select label="Cargo"  :rules="[requiredSelect]" v-model="employee.job" :items="jobList" item-text="value" item-value="key"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-select label="Tipo de pagamento" :rules="[requiredSelect]" v-model="employee.typeOfSalary" :items="typeOfSalaryList" item-text="value" item-value="key"/>
              </v-col>
              <v-col>
                <v-text-field label="Salário" :rules="[required, number]" v-model="employee.salaryValue"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-select label="Perfil de acesso" :rules="[requiredSelect]" v-model="employee.roleList"
                  :items="roleEmployeeList" item-text="value" item-value="key" multiple/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn text @click="currentStep--"> Anterior </v-btn>
                <v-btn color="primary" class="mr-4"  @click="currentStep++"> Próximo </v-btn>
                <v-btn text @click="currentStep = 5"> Finalizar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-stepper-content>
        <v-stepper-content step="5">
          <v-row>
              <app-card-data>
                <v-card-text slot="content">
                  <v-row>
                    <app-map label="Nome" :value="employee.person.name" />
                    <app-map label="Apelido" :value="employee.person.nickName" />
                  </v-row>
                  <v-row>
                    <app-map label="CPF" :value="employee.person.documentNumber1" />
                    <app-map label="Documento 2" :value="employee.person.documentNumber2" />
                  </v-row>
                  <v-row>
                    <app-map label="Data Nascimento" :value="employee.person.bornDate" />
                    <app-map label="Gênero" :value="genreEnum[employee.person.genre]" />
                  </v-row>
                  <v-row>
                    <app-map label="Medicamentos" :value="employee.medicinContinuous ? 'Sim - ' + employee.medicinNotes : 'Não'" />
                    <app-map label="Alergias" :value="employee.allergiesContinuous ? 'Sim ' + employee.allergiesNotes : 'Não'" />
                  </v-row>
                </v-card-text>
              </app-card-data>
              <app-card-data>
                <v-card-text slot="content">
                  <v-row>
                    <app-map label="Endereço" :value="getAddress()" />
                  </v-row>
                  <v-row>
                    <app-map label="Telefone principal" :value="employee.phone1" />
                    <app-map label="Telefone secundário" :value="employee.phone2" />
                  </v-row>
                  <v-row>
                    <app-map label="Email" :value="employee.email" />
                    <app-map label="Instagram" :value="employee.instagram" />
                    <app-map label="Facebook" :value="employee.facebook" />
                  </v-row>
                </v-card-text>
              </app-card-data>
              <app-card-data>
                <v-card-text slot="content">
                  <v-row>
                    <app-map label="Status" :value="employeeStatusEnum[employee.status]" />
                    <app-map label="Cargo" :value="jobEnum[employee.job]" />
                  </v-row>
                  <v-row>
                    <app-map label="Tipo de pagamento" :value="typeOfSalaryEnum[employee.typeOfSalary]" />
                    <app-map label="Salário" :value="employee.salaryValue" />
                  </v-row>
                  <v-row>
                    <v-col class="py-1">
                      <app-label label="Perfis de acesso" />
                      <v-chip v-for="item of employee.roleList" :key="item" class="ma-2">
                        {{ roleEmployeeEnum[item] }}
                      </v-chip>
                    </v-col>
                  </v-row>
                </v-card-text>
              </app-card-data>
          </v-row>
          <v-row align="end">
              <v-col align="right">
                <v-btn text @click="backToList"> Cancelar </v-btn>
                <v-btn color="primary"  @click="save"> Salvar </v-btn>
              </v-col>
            </v-row>
        </v-stepper-content>
      </v-stepper-items>
      <v-snackbar v-model="snackbar.show" :timeout="snackbar.timeout" :color="snackbar.color">
        {{ snackbar.text }}
        <template v-slot:action="{ attrs }">
          <v-btn color="blue" text v-bind="attrs" @click="snackbar.show = false">
            Close
          </v-btn>
        </template>
      </v-snackbar>
    </v-stepper>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import AppMap from '@/components/AppMap.vue'
import AppCardData from '@/components/AppCardDataView.vue'
import { GenreList, GenreEnum } from '@/model/GenreEnum'
import { EmployeeStatusList, EmployeeStatusEnum } from '@/model/EmployeeStatusEnum'
import { JobList, JobEnum } from '@/model/JobEnum'
import { TypeOfSalaryList, TypeOfSalaryEnum } from '@/model/TypeOfSalaryEnum'
import { TypeOfPersonEnum, TypeOfPersonList, TypeOfPerson } from '@/model/TypeOfPersonEnum'
import InputDate from '@/components/InputDate.vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'
import { EmployeeRolesList, EmployeeRolesEnum } from '@/model/EmployeeRolesEnum'
import AppLabel from '@/components/AppLabel.vue'

export default Vue.extend({
  components: {
    InputDate,
    AppMap,
    AppCardData,
    AppLabel
  },
  data () {
    return {
      roleEmployeeList: EmployeeRolesList,
      roleEmployeeEnum: EmployeeRolesEnum,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      genreList: GenreList,
      genreEnum: GenreEnum,
      employeeStatusList: EmployeeStatusList,
      employeeStatusEnum: EmployeeStatusEnum,
      jobList: JobList,
      jobEnum: JobEnum,
      typeOfSalaryList: TypeOfSalaryList,
      typeOfSalaryEnum: TypeOfSalaryEnum,
      validentifier: false,
      validSteps: [true, true, true, true],
      currentStep: 5,
      employee: {
        identifier: this.$route.params.identifier,
        salaryValue: 0.0,
        roleList: [] as any,
        status: null,
        medicinContinuous: false as any,
        allergiesContinuous: false as any,
        person: {
          name: null as any,
          type: Object.keys(TypeOfPersonEnum)[0],
          bornDate: null as any,
          nickName: null as any,
          // genre: 'MALE',
          address: {
            street: null,
            number: null,
            complement: null,
            neighborhood: {
              name: null,
              city: {
                identifier: null,
                name: '',
                state: {
                  identifier: null,
                  name: '',
                  country: {
                    identifier: null,
                    name: ''
                  }
                }
              }
            }
          }
        }
      },
      cities: [],
      states: [],
      countries: []
    }
  },
  watch: {
    currentStep: {
      handler (newValue, oldValue) {
        this.validStep(oldValue)
      }
    }
  },
  methods: {
    validStep (_step: number) {
      const form = this.$refs['formStepper' + _step]
      if (form) {
        this.validSteps[_step - 1] = (this.$refs['formStepper' + _step] as Vue & { validate: () => boolean }).validate()
      }
    },
    getValue (value: any, prefix?: any) {
      let ret = ''
      if (value) {
        ret += (prefix || ' ') + value
      }

      return ret
    },
    getAddress () {
      return this.getValue(this.employee.person.address.street) +
        this.getValue(this.employee.person.address.number, ', ') +
        this.getValue(this.employee.person.address.complement) +
        this.getValue(this.employee.person.address.neighborhood.name, ', ') +
        this.getValue(this.employee.person.address.neighborhood.city.name, ', ') +
        this.getValue(this.employee.person.address.neighborhood.city.state.name, ' - ') || '-'
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
    number (value: any) {
      if (value && value.length > 0) {
        const lastChar = value.charAt(value.length - 1)
        if (!lastChar.match(/^[0-9]*\.?[0-9]*$/)) {
          this.employee.salaryValue = value.slice(0, -1)
        }
        const valueSplit = value.split('.')
        if (valueSplit.length > 2) {
          valueSplit.splice(2)
          this.employee.salaryValue = valueSplit.join('.')
          value = this.employee.salaryValue
        }

        if (valueSplit[1] && valueSplit[1].length > 2) {
          valueSplit[1] = valueSplit[1].substring(0, 2)
          this.employee.salaryValue = valueSplit.join('.')
          value = this.employee.salaryValue
        }

        if (!value.match(/^\d{0,5}(?:\.\d{1,2})?$/)) {
          return 'Invalid format'
        }
      }
      return true
    },
    required (value: any) {
      return !!value || 'Required.'
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
    counter (value: any) {
      return value.length <= 20 || 'Max 20 characters'
    },
    email (value: any) {
      const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return pattern.test(value) || 'Invalid e-mail.'
    },
    setBornDate (date: any) {
      this.employee.person.bornDate = date
    },
    backToList () {
      this.$router.push('/panel/register/person/employee').catch(_err => { console.log(_err) })
    },
    async save () {
      for (const form of Object.keys(this.$refs)) {
        if (!(this.$refs[form] as Vue & { validate: () => boolean }).validate()) {
          this.snackbar.show = true
          this.snackbar.text = 'Preencha todos os campos obrigatórios'
          this.snackbar.timeout = 0
          return
        }
      }
      try {
        if (this.employee.identifier) {
          await httpAPI.put('/employee', this.employee)
        } else {
          await httpAPI.post('/employee', this.employee)
        }

        this.snackbar.show = true
        this.snackbar.text = 'Dados salvos com sucesso'
        this.snackbar.color = 'success'
        this.backToList()
      } catch (error) {
        this.snackbar.text = 'Erro ao processar: ' + error
        this.snackbar.show = true
      }
    },
    clear () {
      this.currentStep = 1
      this.employee = {
        identifier: '' as any,
        salaryValue: 0,
        roleList: [] as any,
        status: null,
        medicinContinuous: false as any,
        allergiesContinuous: false as any,
        person: {
          name: '',
          type: Object.keys(TypeOfPersonEnum)[0],
          bornDate: '',
          nickName: '',
          // genre: 'MALE',
          address: {
            street: null,
            number: null,
            complement: null,
            neighborhood: {
              name: null,
              city: {
                identifier: null,
                name: '',
                state: {
                  identifier: null,
                  name: '',
                  country: {
                    identifier: null,
                    name: ''
                  }
                }
              }
            }
          }
        }
      }
    },
    async getEmployee () {
      if (this.employee.identifier) {
        const search = {
          columnList: [
            'identifier',
            'job', 'status', 'creationDate',
            'medicinContinuous',
            'medicinNotes',
            'allergiesContinuous',
            'allergiesNotes',
            'email',
            'phone1',
            'phone2',
            'instagram',
            'facebook',
            'typeOfSalary',
            'salaryValue',
            'person.name',
            'person.genre',
            'person.nickName',
            'person.type',
            'person.documentNumber1',
            'person.documentNumber2',
            'person.bornDate',
            'person.address.zipCode',
            'person.address.street',
            'person.address.number',
            'person.address.complement',
            'person.address.neighborhood.identifier',
            'person.address.neighborhood.name',
            'person.address.neighborhood.city.identifier',
            'person.address.neighborhood.city.name',
            'person.address.neighborhood.city.state.identifier',
            'person.address.neighborhood.city.state.name',
            'person.address.neighborhood.city.state.country.identifier',
            'person.address.neighborhood.city.state.country.name'
          ],
          resultUnique: true,
          example: {
            identifier: this.employee.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/employee/search', search)
        this.employee = response.data.data.result[0]
        console.log(this.employee)
      }
    },
    async getCities () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/city/search', search)
      this.cities = response.data.data.result
    },
    async getStates () {
      const search = {
        columnList: [
          'identifier',
          'name',
          'country.identifier',
          'country.name'
        ],
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/state/search', search)
      this.states = response.data.data.result
    },
    async getCountries () {
      const search = {
        columnList: [
          'identifier',
          'name'
        ],
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/country/search', search)
      this.countries = response.data.data.result
    }
  },
  async created () {
    await this.getCountries()
    await this.getStates()
    await this.getCities()
    if (this.employee.identifier) {
      await this.getEmployee()
    }
  }
})
</script>
