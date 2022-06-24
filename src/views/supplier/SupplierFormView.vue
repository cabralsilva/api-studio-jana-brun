<template>
    <div>
      <v-stepper v-model="currentStep">
        <v-stepper-header>
          <v-stepper-step :complete="currentStep == 1" step="1" editable :rules="[() => validSteps[0]]">
            Informações básicas
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
                  <v-text-field v-model="supplier.person.name" :rules="[required]" label="Razao social/Nome"/>
                </v-col>
                <v-col>
                  <v-text-field v-model="supplier.person.nickName" label="Nome fantasia"/>
                </v-col>
                <v-col>
                  <v-select v-model="supplier.person.type" :rules="[requiredSelect]" :items="typeOfPersonList" item-text="value" item-value="key" label="Tipo" />
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field v-model="supplier.person.documentNumber1" :rules="[required, onlyNumber]" label="CPF/CNPJ"/>
                </v-col>
                <v-col>
                  <v-text-field v-model="supplier.person.documentNumber2" label="Documento 2"/>
                </v-col>
                <v-col>
                  <v-select v-model="supplier.type" :rules="[requiredSelect]" :items="typeOfSupplierList" item-text="value" item-value="key" label="Categoria de fornecedor" />
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
                  <v-text-field v-model="supplier.person.address.zipCode" label="CEP" :rules="[required]"/>
                </v-col>
                <v-col>
                  <v-text-field v-model="supplier.person.address.street" label="Avenida/Rua" :rules="[required]"/>
                </v-col>
                <v-col class="col-2">
                  <v-text-field v-model="supplier.person.address.number" label="Número" :rules="[required]"/>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field v-model="supplier.person.address.complement" label="Complemento"/>
                </v-col>
                <v-col>
                  <v-text-field v-model="supplier.person.address.neighborhood.name" label="Bairro" :rules="[required]"/>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-select :rules="[requiredSelect]" v-model="supplier.person.address.neighborhood.city" :items="cities" item-text="name" item-value="identifier" return-object label="Cidade" />
                </v-col>
                <v-col>
                  <v-select :rules="[requiredSelect]" v-model="supplier.person.address.neighborhood.city.state" :items="states" item-text="name" item-value="identifier" return-object label="Estado" />
                </v-col>
                <v-col>
                  <v-select :rules="[requiredSelect]" v-model="supplier.person.address.neighborhood.city.state.country" :items="countries" item-text="name" item-value="identifier" return-object label="País" />
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
                  <v-text-field v-model="supplier.email" label="Email" :rules="[required, email]" />
                </v-col>
                <v-col>
                  <v-text-field v-model="supplier.phone1" label="Telefone principal" :rules="[required]"/>
                </v-col>
                <v-col>
                  <v-text-field v-model="supplier.phone2" label="Telefone secundário"/>
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
                <v-col cols="3">
                  <v-select :rules="[requiredSelect]" v-model="supplier.status" :items="statusActiveList" item-text="value" item-value="key" label="Status" />
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
                      <app-map label="Razão social" :value="supplier.person.name" />
                      <app-map label="Nome fantasia" :value="supplier.person.nickName" />
                    </v-row>
                    <v-row>
                      <app-map label="CPF/CNPJ" :value="supplier.person.documentNumber1" />
                      <app-map label="Documento 2" :value="supplier.person.documentNumber2" />
                    </v-row>
                    <v-row>
                      <app-map label="Tipo" :value="typeOfPersonEnum[supplier.person.type]" />
                      <app-map label="Categoria do fornecedor" :value="typeOfSupplierEnum[supplier.type]" />
                    </v-row>
                  </v-card-text>
                </app-card-data>
                <app-card-data>
                  <v-card-text slot="content">
                    <v-row>
                      <app-map label="Endereço" :value="getAddress()" />
                    </v-row>
                    <v-row>
                      <app-map label="Telefone principal" :value="supplier.phone1" />
                      <app-map label="Telefone secundário" :value="supplier.phone2" />
                    </v-row>
                    <v-row>
                      <app-map label="Email" :value="supplier.email" />
                    </v-row>
                  </v-card-text>
                </app-card-data>
                <app-card-data>
                  <v-card-text slot="content">
                    <v-row>
                      <app-map label="Status" :value="statusActiveEnum[supplier.status]" />
                    </v-row>
                  </v-card-text>
                </app-card-data>
            </v-row>
            <v-row align="end">
                <v-col align="right">
                  <v-btn text @click="navigation('/panel/register/person/supplier')"> Cancelar </v-btn>
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
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'
import { StatusActiveList, StatusActiveEnum } from '@/model/StatusActiveEnum'
import { TypeOfPersonEnum, TypeOfPersonList } from '@/model/TypeOfPersonEnum'
import { TypeOfSupplierList, TypeOfSupplierEnum } from '@/model/TypeOfSupplierEnum'
import AppMap from '@/components/AppMap.vue'
import AppCardData from '@/components/AppCardDataView.vue'

export default Vue.extend({
  components: {
    AppMap,
    AppCardData
  },
  data () {
    return {
      statusActiveList: StatusActiveList,
      statusActiveEnum: StatusActiveEnum,
      typeOfSupplierList: TypeOfSupplierList,
      typeOfSupplierEnum: TypeOfSupplierEnum,
      typeOfPersonList: TypeOfPersonList,
      typeOfPersonEnum: TypeOfPersonEnum,
      formValid: false,
      snackbar: {
        show: false,
        text: '',
        color: 'primary',
        timeout: 2000
      },
      validSteps: [true, true, true, true],
      currentStep: 5,
      supplier: {
        identifier: this.$route.params.identifier,
        status: 'ACTIVE',
        email: 'd.nadson@yahoo.com.br',
        phone1: '4198798888',
        phone2: null,
        type: 'MERCHANDISE',
        person: {
          name: 'Daniel',
          nickName: 'Fantasia',
          type: 'INDIVIDUAL',
          documentNumber1: '36985214785236',
          address: {
            zipCode: '39401464',
            street: 'Rua',
            number: '25',
            complement: null,
            neighborhood: {
              name: 'Bairro',
              city: {
                identifier: 1,
                name: 'Belo Horizonte',
                state: {
                  identifier: 1,
                  name: 'Minas Gerais',
                  country: {
                    identifier: 1,
                    name: 'Brasil'
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
  },
  methods: {
    required (value: any) {
      if (isNaN(value)) {
        return !!value || 'Required.'
      }

      return (!!value && value >= 0) || 'Required.'
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
    getValue (value: any, prefix?: any) {
      let ret = ''
      if (value) {
        ret += (prefix || ' ') + value
      }

      return ret
    },
    getAddress () {
      return this.getValue(this.supplier.person.address.street) +
        this.getValue(this.supplier.person.address.number, ', ') +
        this.getValue(this.supplier.person.address.complement) +
        this.getValue(this.supplier.person.address.neighborhood.name, ', ') +
        this.getValue(this.supplier.person.address.neighborhood.city.name, ', ') +
        this.getValue(this.supplier.person.address.neighborhood.city.state.name, ' - ') || '-'
    },
    email (value: any) {
      const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      return pattern.test(value) || 'Invalid e-mail.'
    },
    navigation (to: any) {
      // eslint-disable-next-line
      this.$router.push(to).catch(_err => { })
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
        if (this.supplier.identifier) {
          await httpAPI.put('/supplier', this.supplier)
        } else {
          await httpAPI.post('/supplier', this.supplier)
        }

        this.snackbar.show = true
        this.snackbar.text = 'Dados salvos com sucesso'
        this.snackbar.color = 'success'
        this.navigation('/panel/register/person/supplier')
      } catch (error) {
        this.snackbar.text = 'Erro ao processar: ' + error
        this.snackbar.show = true
      }
    },
    clear () {
      this.currentStep = 1
      this.supplier = {
        identifier: '' as any,
        status: null as any,
        email: null as any,
        phone1: null as any,
        phone2: null,
        type: 'MERCHANDISE',
        person: {
          name: '',
          type: Object.keys(TypeOfPersonEnum)[0],
          nickName: '',
          documentNumber1: '',
          address: {
            zipCode: '39401464',
            street: null as any,
            number: null as any,
            complement: null,
            neighborhood: {
              name: null as any,
              city: {
                identifier: null as any,
                name: '',
                state: {
                  identifier: null as any,
                  name: '',
                  country: {
                    identifier: null as any,
                    name: ''
                  }
                }
              }
            }
          }
        }
      }
    },
    async getSupplier () {
      if (this.supplier.identifier) {
        const search = {
          columnList: [
            'identifier',
            'status',
            'email',
            'phone1',
            'phone2',
            'type',
            'person.name',
            'person.nickName',
            'person.type',
            'person.documentNumber1',
            'person.documentNumber2',
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
            identifier: this.supplier.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/supplier/search', search)
        this.supplier = response.data.data.result[0]
      }
    },
    async getCities () {
      const search = {
        columnList: [
          'identifier',
          'name',
          'state.identifier',
          'state.name',
          'state.country.identifier',
          'state.country.name'
        ],
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
  created () {
    this.getCountries()
    this.getStates()
    this.getCities()
    if (this.supplier.identifier) {
      this.getSupplier()
    }
  }
})
</script>
