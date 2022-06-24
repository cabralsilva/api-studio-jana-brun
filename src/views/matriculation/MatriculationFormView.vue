<template>
  <div>
    <v-stepper v-model="currentStep">
      <v-stepper-header>
        <v-stepper-step :complete="currentStep == 1" step="1" editable :rules="[() => validSteps[0]]">
          Aluno
        </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="currentStep == 2" step="2" editable :rules="[() => validSteps[1]]">
          Responsável
        </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="currentStep == 3" step="3" editable :rules="[() => validSteps[2]]">
          Endereço
        </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="currentStep == 4" step="4" editable :rules="[() => validSteps[2]]">
          Contato
        </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="currentStep == 5" step="5" editable :rules="[() => validSteps[3]]">
          Matrícula
        </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="currentStep == 6" step="6" editable>
          Resumo
        </v-stepper-step>
      </v-stepper-header>
      <v-stepper-items>
        <v-stepper-content step="1">
          <v-form ref="formStepper1" v-model="validSteps[0]" lazy-validation>
            <v-row>
              <v-col>
                <v-text-field v-model="matriculation.student.person.name" :rules="[required]" label="Name"/>
              </v-col>
              <v-col>
                <v-text-field v-model="matriculation.student.person.nickName" label="Apelido"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field v-model="matriculation.student.person.documentNumber1" :rules="[required, onlyNumber]" label="CPF"/>
              </v-col>
              <v-col>
                <v-text-field v-model="matriculation.student.person.documentNumber2" label="Documento 2"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <input-date label="Data de nascimento" :callback="(date) => matriculation.student.person.bornDate = date" :initDate="matriculation.student.person.bornDate"/>
              </v-col>
              <v-col>
                <v-radio-group v-model="matriculation.student.person.genre" row label="Genêro" :rules="[required]">
                  <v-radio v-for="item in genreList" :key="item.key" :label="item.value" :value="item.key" />
                </v-radio-group>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <div class="v-input theme--light v-text-field v-text-field--is-booted">
                  <v-checkbox class="pr-4" v-model="matriculation.student.medicinContinuous" label="Remédio contínuo?"/>
                  <v-text-field v-model="matriculation.student.medicinNotes" label="Quais"/>
                </div>
              </v-col>
              <v-col>
                <div class="v-input theme--light v-text-field v-text-field--is-booted">
                  <v-checkbox class="pr-4" v-model="matriculation.student.allergiesContinuous" label="Alergias?"/>
                  <v-text-field v-model="matriculation.student.allergiesNotes" label="Quais"/>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-select :rules="[requiredSelect]" v-model="matriculation.student.schoolLevel" :items="schoolLevelList" item-text="value" item-value="key" label="Nível escolar" />
              </v-col>
              <v-col>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn color="primary" class="mr-4"  @click="currentStep++"> Próximo </v-btn>
                <v-btn text @click="currentStep = 6"> Finalizar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-stepper-content>
        <v-stepper-content step="2">
          <v-form ref="formStepper2" v-model="validSteps[1]" lazy-validation>
            <v-row>
              <v-col>
                <v-text-field v-model="matriculation.student.responsible.name" :rules="[required]" label="Name"/>
              </v-col>
              <v-col>
                <v-text-field v-model="matriculation.student.responsible.nickName" label="Apelido"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field v-model="matriculation.student.responsible.documentNumber1" :rules="[required, onlyNumber]" label="CPF"/>
              </v-col>
              <v-col>
                <v-text-field v-model="matriculation.student.responsible.documentNumber2" label="Documento 2"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <input-date label="Data de nascimento" :callback="(date) => matriculation.student.responsible.bornDate = date" :initDate="matriculation.student.responsible.bornDate"/>
              </v-col>
              <v-col>
                <v-radio-group v-model="matriculation.student.responsible.genre" row label="Genêro" :rules="[required]">
                  <v-radio v-for="item in genreList" :key="item.key" :label="item.value" :value="item.key" />
                </v-radio-group>
              </v-col>
              <v-col cols="12">
                <v-textarea label="Observação" v-model="matriculation.observation" counter no-resize />
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn text @click="currentStep--"> Anterior </v-btn>
                <v-btn color="primary" class="mr-4"  @click="currentStep++"> Próximo </v-btn>
                <v-btn text @click="currentStep = 6"> Finalizar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-stepper-content>
        <v-stepper-content step="3">
          <v-form ref="formStepper3" v-model="validSteps[2]" lazy-validation>
            <v-row>
              <v-col class="col-2">
                <v-text-field v-model="matriculation.student.responsible.address.zipCode" label="CEP" :rules="[required]"/>
              </v-col>
              <v-col>
                <v-text-field v-model="matriculation.student.responsible.address.street" label="Avenida/Rua" :rules="[required]"/>
              </v-col>
              <v-col class="col-2">
                <v-text-field v-model="matriculation.student.responsible.address.number" label="Número" :rules="[required]"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field v-model="matriculation.student.responsible.address.complement" label="Complemento"/>
              </v-col>
              <v-col>
                <v-text-field v-model="matriculation.student.responsible.address.neighborhood.name" label="Bairro" :rules="[required]"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-select :rules="[requiredSelect]" v-model="matriculation.student.responsible.address.neighborhood.city" :items="cities" item-text="name" item-value="identifier" return-object label="Cidade" />
              </v-col>
              <v-col>
                <v-select :rules="[requiredSelect]" v-model="matriculation.student.responsible.address.neighborhood.city.state" :items="states" item-text="name" item-value="identifier" return-object label="Estado" />
              </v-col>
              <v-col>
                <v-select :rules="[requiredSelect]" v-model="matriculation.student.responsible.address.neighborhood.city.state.country" :items="countries" item-text="name" item-value="identifier" return-object label="País" />
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn text @click="currentStep--"> Anterior </v-btn>
                <v-btn color="primary" class="mr-4"  @click="currentStep++"> Próximo </v-btn>
                <v-btn text @click="currentStep = 6"> Finalizar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-stepper-content>
        <v-stepper-content step="4">
          <v-form ref="formStepper4" v-model="validSteps[3]" lazy-validation>
            <v-row>
              <v-col>
                <v-text-field v-model="matriculation.student.responsibleEmail" label="Email" :rules="[required, email]" />
              </v-col>
              <v-col>
                <v-text-field v-model="matriculation.student.responsiblePhone1" label="Telefone principal" :rules="[required]"/>
              </v-col>
              <v-col>
                <v-text-field v-model="matriculation.student.responsiblePhone2" label="Telefone secundário"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-text-field v-model="matriculation.student.responsibleFacebook" label="Facebook"/>
              </v-col>
              <v-col>
                <v-text-field v-model="matriculation.student.responsibleInstagram" label="Instagram"/>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn text @click="currentStep--"> Anterior </v-btn>
                <v-btn color="primary" class="mr-4"  @click="currentStep++"> Próximo </v-btn>
                <v-btn text @click="currentStep = 6"> Finalizar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-stepper-content>
        <v-stepper-content step="5">
          <v-form ref="formStepper5" v-model="validSteps[4]" lazy-validation>
            <v-row>
              <v-col class="col-2">
                <v-select label="Situação" :rules="[requiredSelect]" v-model="matriculation.status" :items="matriculationStatusList" item-text="value" item-value="key" />
              </v-col>
              <v-col>
                <v-select v-model="matriculation.classList" :items="this.classList"
                  item-text="description" item-value="identitifer"
                  attach chips label="Turmas" multiple return-object/>
              </v-col>
            </v-row>
            <v-form ref="formAddProduct" v-model="validSteps[4]" lazy-validation>
              <v-row class="elevation-6 ma-0">
                <v-col>
                  <v-row>
                    <v-col class="col-3">
                      <v-select v-model="itemEdit.product" :items="this.productList"
                        item-text="description" item-value="identitifer"
                        attach chips label="Selecione o produto" return-object
                        @change="selectProduct(itemEdit.product)"/>
                    </v-col>
                    <component v-for="(grate, index) in itemEdit.product.grateList" :key="index" :is="currentProduct"
                    :label="grate.description"
                    :callback="(select) => selectGrateItem(select)"
                    :params="grate.identifier"
                    :asyncLoading="getGrateItemList"/>
                    <v-col class="col-3">
                      <v-text-field v-model="itemEdit.quantity" label="Quantidade"/>
                    </v-col>
                  </v-row>
                  <v-row align="end">
                    <v-col align="right">
                      <template v-if="itemEdit.unitPrice">
                        <span class="pr-4" style="font-size: 12px">{{ `R$ ${itemEdit.unitPrice} x ${itemEdit.quantity} ` }}</span>
                        <span class="pr-4 subheading">Total: <b style="font-size: 18px">{{ `R$ ${itemEdit.totalPrice}` }}</b></span>
                      </template>
                      <v-btn :disabled="!itemEdit.unitPrice" color="primary" @click="addItem"> Add </v-btn>
                    </v-col>
                  </v-row>
                </v-col>
              </v-row>
            </v-form>
            <v-row>
              <v-col>
                <v-simple-table>
                  <template v-slot:default>
                    <thead>
                      <tr>
                        <th class="text-left">
                          Produto
                        </th>
                        <th class="text-left">
                          Especificação
                        </th>
                        <th class="text-left">
                          Preço Unit.
                        </th>
                        <th class="text-left">
                          Quantidade
                        </th>
                        <th class="text-left">
                          Preço Total
                        </th>
                        <th class="text-left">
                          Ação
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="(item, index) in matriculation.matriculationItemList" :key="item.identifier">
                        <td>
                          <b>{{ item.product.description }}</b><br/>
                        </td>
                        <td>
                          <v-row>
                            <v-col v-for="grateItem of item.grateItemList" :key="grateItem.identifier">
                              <v-card class="pa-0" outlined  flat color="transparent">
                                <v-card-text class="pa-0">
                                  <span class="subheading">{{ grateItem.grate.description }}</span>
                                  <v-chip-group active-class="deep-purple--text text--accent-4">
                                    <v-chip class="ma-2">
                                      {{ `${grateItem.value}`}}
                                    </v-chip>
                                  </v-chip-group>
                                </v-card-text>
                              </v-card>
                            </v-col>
                          </v-row>
                        </td>
                        <td>{{ `R$ ${item.unitPrice}` }}</td>
                        <td>{{ `x ${item.quantity}` }}</td>
                        <td>{{ `R$ ${item.totalPrice}` }}</td>
                        <td><v-icon @click="removeItem(index)">mdi-delete-forever</v-icon></td>
                      </tr>
                    </tbody>
                  </template>
                </v-simple-table>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <v-btn text @click="currentStep--"> Anterior </v-btn>
                <v-btn color="primary" class="mr-4"  @click="currentStep++"> Próximo </v-btn>
                <v-btn text @click="currentStep = 6"> Finalizar </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-stepper-content>
        <v-stepper-content step="6">
          <v-row>
            <app-card-data :columnSize="6">
              <v-card-title slot="title">Aluno</v-card-title>
              <v-card-text slot="content">
                <v-row>
                  <app-map label="Nome" :value="matriculation.student.person.name" />
                  <app-map label="Apelido" :value="matriculation.student.person.nickName" />
                  <app-map label="CPF" :value="matriculation.student.person.documentNumber1" />
                  <app-map label="Documento 2" :value="matriculation.student.person.documentNumber2" />
                </v-row>
                <v-row>
                  <app-map label="Data Nascimento" :value="matriculation.student.person.bornDate" />
                  <app-map label="Gênero" :value="genreEnum[matriculation.student.person.genre]" />
                  <app-map label="Medicamentos" :value="matriculation.student.medicinContinuous ? 'Sim - ' + matriculation.student.medicinNotes : 'Não'" />
                  <app-map label="Alergias" :value="matriculation.student.allergiesContinuous ? 'Sim ' + matriculation.student.allergiesNotes : 'Não'" />
                </v-row>
                <v-row>
                  <app-map label="Nível escolar" :value="schoolLevelEnum[matriculation.student.schoolLevel]" />
                </v-row>
              </v-card-text>
            </app-card-data>
            <app-card-data :columnSize="6">
              <v-card-title slot="title">Responsável</v-card-title>
              <v-card-text slot="content">
                <v-row>
                  <app-map label="Nome do aluno" :value="matriculation.student.responsible.name" />
                  <app-map label="Apelido" :value="matriculation.student.responsible.nickName" />
                  <app-map label="CPF" :value="matriculation.student.responsible.documentNumber1" />
                  <app-map label="Documento 2" :value="matriculation.student.responsible.documentNumber2" />
                </v-row>
                <v-row>
                  <app-map label="Data Nascimento" :value="matriculation.student.responsible.bornDate" />
                  <app-map label="Gênero" :value="genreEnum[matriculation.student.responsible.genre]" />
                  <app-map label="Telefone principal" :value="matriculation.student.responsiblePhone1" />
                  <app-map label="Telefone secundário" :value="matriculation.student.responsiblePhone2" />
                </v-row>
                <v-row>
                  <app-map label="Email" :value="matriculation.student.responsibleEmail" />
                  <app-map label="Instagram" :value="matriculation.student.responsibleInstagram" />
                  <app-map label="Facebook" :value="matriculation.student.responsibleFacebook" />
                </v-row>
                <v-row>
                  <app-map label="Endereço" :value="getAddress()" />
                </v-row>
                <v-row>
                  <app-map label="Observação" :value="matriculation.observation" />
                </v-row>
              </v-card-text>
            </app-card-data>
          </v-row>
          <v-row>
            <app-card-data :columnSize="12">
              <v-card-title slot="title">Matrícula</v-card-title>
              <v-card-text slot="content">
                <v-row>
                  <app-map label="Número" :value="matriculation.identifier ? matriculation.identifier : 'N/D'" />
                  <app-map label="Status" :value="matriculationStatusEnum[matriculation.status]" />
                  <app-map label="Turmas" :value="matriculation.classList" />
                </v-row>
                <v-row>
                  <v-col class="py-1">
                    <app-label label="Produtos" />
                    <v-simple-table>
                      <template v-slot:default>
                        <tbody>
                          <tr v-for="item of matriculation.matriculationItemList" :key="item.identifier">
                            <td class="col-1">
                              <app-label :label="`${item.quantity}x `" />
                            </td>
                            <td>
                              <b>{{ `${item.product.description}` }}</b>{{ `${getSpecification(item.grateItemList)} ` }}
                            </td>
                            <td class="col-3">
                              <div align="right">
                                <b>{{ `R$ ${item.totalPrice}` }}</b>
                              </div>
                            </td>
                          </tr>
                          <tr>
                            <td colspan="6">
                              <v-col align="right">
                                Total <b style="font-size: 24px">{{ `R$ ${getTotalPrice().toFixed(2)}` }} </b>
                              </v-col>
                            </td>
                          </tr>
                        </tbody>
                      </template>
                    </v-simple-table>
                  </v-col>
                </v-row>
              </v-card-text>
            </app-card-data>
          </v-row>
          <v-row align="end">
            <v-col align="right">
              <v-btn :disabled="matriculation.status !== 'EFFECTIVE'" color="orange"> Suspender </v-btn>
              <v-btn :disabled="!!matriculation.identifier" text @click="backToList"> Cancelar </v-btn>
              <v-btn :disabled="!!matriculation.identifier" color="primary"  @click="save"> Salvar </v-btn>
            </v-col>
          </v-row>
          <v-row>
            <app-card-data :columnSize="12">
              <v-card-title slot="title">Pagamentos</v-card-title>
              <v-card-text slot="content">
                <v-row>
                  <v-col>
                    <app-label label="Total da matrícula" />
                    <b class="text-h5">{{ `R$ ${ getTotalPrice().toFixed(2)}` }}</b>
                  </v-col>
                  <v-col>
                    <app-label label="Lançado" />
                    <b class="text-h5">{{ `R$ ${ getPaidValue().toFixed(2) }` }}</b>
                  </v-col>
                  <v-col>
                    <app-label label="Em aberto" />
                    <b class="text-h5">{{ `R$ ${ getOpenValue().toFixed(2) }` }}</b>
                    <v-dialog v-model="modalPayment">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn :disabled="isDisablePayment()" class="ml-2" align-self="top" color="secondary" v-bind="attrs" v-on="on">
                          Pagar
                        </v-btn>
                      </template>
                      <bill-to-receive-form-view :key="componentKey"
                        :dialog="true"
                        :maxValue="getOpenValue()"
                        :matriculation="matriculation"
                        :customer="matriculation.responsible"
                        :discountFirstInstallment="enabledDiscountOnFirstInstallment()"
                        :indexTitle="getIndexTitle()"
                        :callback="updatePayments" />
                    </v-dialog>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col class="py-6">
                    <v-simple-table>
                      <template v-slot:default>
                        <tbody>
                          <tr class="row" v-for="billToReceive of billToReceiveList" :key="billToReceive.identifier">
                            <td class="col">
                              <app-label :label="`${billToReceive.title}`" />
                            </td>
                            <td class="col">
                              <app-label :label="`R$ ${billToReceive.value}`" />
                            </td>
                            <td class="col">
                              <app-label :label="`${billToReceive.paymentCondition.quantityInstallments}x`" />
                            </td>
                            <td class="col">
                              <v-chip color="blue">
                                {{ `${billToReceive.status}` }}
                              </v-chip>
                            </td>
                          </tr>
                        </tbody>
                      </template>
                    </v-simple-table>
                  </v-col>
                </v-row>
              </v-card-text>
            </app-card-data>
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
import { GenreList, GenreEnum } from '@/model/GenreEnum'
import { EmployeeStatusList, EmployeeStatusEnum } from '@/model/EmployeeStatusEnum'
import { JobList, JobEnum } from '@/model/JobEnum'
import { TypeOfSalaryList, TypeOfSalaryEnum } from '@/model/TypeOfSalaryEnum'
import { SchoolLevelList, SchoolLevelEnum } from '@/model/SchoolLevelEnum'
import { TypeOfPersonEnum, TypeOfPersonList, TypeOfPerson } from '@/model/TypeOfPersonEnum'
import InputDate from '@/components/InputDate.vue'
import httpAPI from '@/plugins/axios'
import { HttpSearchRequest } from '@/model/HttpUtils'
import AppSelect from '@/components/AppSelect.vue'
import AppMap from '@/components/AppMap.vue'
import AppLabel from '@/components/AppLabel.vue'
import AppCardData from '@/components/AppCardDataView.vue'
import { MatriculationStatusEnum, MatriculationStatusList } from '@/model/MatriculationStatusEnum'
import BillToReceiveFormView from '@/views/financial/bill-to-receive/BillToReceiveFormView.vue'
import { ProductCategoryList, ProductCategoryEnum } from '@/model/ProductCategoryEnum'
import moment from 'moment'

export default Vue.extend({
  components: {
    InputDate,
    AppSelect,
    AppMap,
    AppCardData,
    AppLabel,
    BillToReceiveFormView
  },
  data () {
    return {
      componentKey: 0,
      disablePayment: false,
      modalPayment: false,
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
      matriculationStatusList: MatriculationStatusList,
      matriculationStatusEnum: MatriculationStatusEnum,
      schoolLevelList: SchoolLevelList,
      schoolLevelEnum: SchoolLevelEnum,
      jobList: JobList,
      jobEnum: JobEnum,
      typeOfSalaryList: TypeOfSalaryList,
      typeOfSalaryEnum: TypeOfSalaryEnum,
      validentifier: false,
      validSteps: [true, true, true, true, true],
      currentStep: 6,
      matriculation: {
        identifier: this.$route.params.identifier,
        status: null as any,
        observation: null as any,
        medicinContinuous: null as any,
        medicinNotes: null as any,
        allergiesContinuous: null as any,
        allergiesNotes: null as any,
        responsibleEmail: null as any,
        responsiblePhone1: null as any,
        responsiblePhone2: null as any,
        responsibleInstagram: null as any,
        responsibleFacebook: null as any,
        classList: [],
        matriculationItemList: [] as any,
        student: {
          name: null as any,
          nickName: null as any,
          type: Object.keys(TypeOfPersonEnum)[0],
          bornDate: null as any,
          genre: 'MALE',
          schoolLevel: '',
          school: null as any,
          person: {
            name: null as any,
            nickName: null as any,
            type: Object.keys(TypeOfPersonEnum)[0],
            bornDate: null as any,
            genre: 'MALE'
          },
          responsible: {
            name: null as any,
            nickName: null as any,
            type: Object.keys(TypeOfPersonEnum)[0],
            bornDate: null as any,
            genre: 'MALE',
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
        financial: {
          totalPrice: 0 as number,
          openValue: 0 as number,
          paidValue: 0 as number,
          additionValue: 0 as number
        }
      },
      cities: [],
      states: [],
      countries: [],
      productList: [],
      classList: [],
      itemEdit: {
        product: {
          identifier: null as any,
          category: null as any
        },
        unitPrice: null as any,
        quantity: 1 as number,
        totalPrice: null as any,
        additionPercent: null as any,
        finalPrice: null as any,
        grateItemList: [] as any
      },
      billToReceiveList: []
    }
  },
  watch: {
    currentStep: {
      handler (newValue, oldValue) {
        this.validStep(oldValue)
      }
    },
    itemEdit: {
      handler (newValue, oldValue) {
        this.getPriceTotalItem()
      },
      deep: true
    }
  },
  computed: {
    currentProduct () {
      return 'app-select'
    }
  },
  methods: {
    enabledDiscountOnFirstInstallment () {
      if (this.billToReceiveList.length === 0 && moment().date() > 30) {
        return true
      }
      return false
    },
    getIndexTitle () {
      return this.billToReceiveList.length + 1
    },
    updatePayments () {
      this.modalPayment = false
      this.getBillToReceiveList()
    },
    isDisablePayment () {
      if (!this.matriculation.identifier) {
        return true
      }

      if (this.matriculation.financial?.openValue <= 0) {
        return true
      }

      if (this.disablePayment) {
        return true
      }

      return false
    },
    proccessFinancial () {
      this.matriculation.financial = {
        totalPrice: 0 as number,
        openValue: 0 as number,
        paidValue: 0 as number,
        additionValue: 0 as number
      }
      this.matriculation.financial.totalPrice = this.getTotalPrice()
      this.matriculation.financial.paidValue = this.getPaidValue()
      this.matriculation.financial.openValue = this.getOpenValue()
      this.componentKey++
    },
    getTotalPrice () {
      let totalPrice = 0
      this.matriculation?.matriculationItemList?.forEach((item: any) => {
        totalPrice += item.totalPrice
      })
      return totalPrice
    },
    getValueFinancial (value: any): number {
      return Number(Number.parseFloat(value).toFixed(2))
    },
    getOpenValue () {
      return this.getValueFinancial(this.matriculation.financial?.totalPrice - this.matriculation.financial?.paidValue)
    },
    getPaidValue () {
      let paidValue = 0
      this.billToReceiveList?.forEach((item: any) => {
        paidValue += item.value
      })
      return paidValue
    },
    getSpecification (grateItemList: []) {
      let ret = ''
      grateItemList.forEach((grateItem: any) => {
        ret += ` | ${grateItem.grate.description}: ${grateItem.value}`
      })
      return ret
    },
    selectGrateItem (grateItem: any) {
      for (const grateItemAux of this.itemEdit.grateItemList) {
        if (grateItemAux.grate.identifier === grateItem.grate.identifier) {
          this.itemEdit.grateItemList.splice(this.itemEdit.grateItemList.indexOf(grateItemAux), 1)
        }
      }
      this.itemEdit.grateItemList.push(grateItem)
    },
    async selectProduct (product: any) {
      this.itemEdit.product = {
        identifier: null as any,
        category: null as any
      }
      setTimeout(() => {
        this.itemEdit.product = product
        this.itemEdit.unitPrice = product.unitPrice
      }, 10)
    },
    async getPriceTotalItem () {
      if (this.itemEdit.product.identifier) {
        const response = await httpAPI.post('/price-table-item/get-by-cart-item', this.itemEdit)
          .then((res: any) => {
            this.itemEdit.unitPrice = res.data.data.price
            this.itemEdit.totalPrice = this.itemEdit.unitPrice * this.itemEdit.quantity
          })
          .catch((err: any) => {
            console.log(err)
            this.itemEdit.unitPrice = undefined
            this.itemEdit.totalPrice = undefined
          })
      }
    },
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
      return this.getValue(this.matriculation.student.responsible.address.street) +
        this.getValue(this.matriculation.student.responsible.address.number, ', ') +
        this.getValue(this.matriculation.student.responsible.address.complement) +
        this.getValue(this.matriculation.student.responsible.address.neighborhood.name, ', ') +
        this.getValue(this.matriculation.student.responsible.address.neighborhood.city.name, ', ') +
        this.getValue(this.matriculation.student.responsible.address.neighborhood.city.state.name, ' - ') || '-'
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
    addItem () {
      if (!(this.$refs.formAddProduct as Vue & { validate: () => boolean }).validate()) {
        this.snackbar.show = true
        this.snackbar.text = 'Preencha todos os campos obrigatórios'
        this.snackbar.timeout = 0
        return
      }

      // if (this.itemEdit.product.category === Object.keys(ProductCategoryEnum)[Object.values(ProductCategoryEnum).indexOf(ProductCategoryEnum.LESSON as unknown as ProductCategoryEnum)]) {
      //   if (this.matriculation?.matriculationItemList.filter((e: any) => e.product.category === Object.keys(ProductCategoryEnum)[Object.values(ProductCategoryEnum).indexOf(ProductCategoryEnum.LESSON as unknown as ProductCategoryEnum)]).length > 0) {
      //     this.snackbar.show = true
      //     this.snackbar.text = 'Esta matrícula já possui produto da categoria Aulas'
      //     this.snackbar.timeout = 0
      //     return
      //   }
      // }

      this.matriculation.matriculationItemList.push(this.itemEdit)
      this.itemEdit = {
        product: {
          identifier: null as any,
          category: null as any
        },
        unitPrice: null,
        quantity: 1 as number,
        totalPrice: null,
        additionPercent: null,
        finalPrice: null,
        grateItemList: []
      }
      this.proccessFinancial()
    },
    removeItem (index: any) {
      this.matriculation.matriculationItemList.splice(index, 1)
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
    backToList () {
      this.$router.push('/panel/matriculation').catch(_err => { console.log(_err) })
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
        if (this.matriculation.identifier) {
          const response = await httpAPI.put('/matriculation', this.matriculation)
        } else {
          const response = await httpAPI.post('/matriculation', this.matriculation)
          this.matriculation.identifier = response.data.data.identifier
        }

        this.snackbar.show = true
        this.snackbar.text = 'Dados salvos com sucesso'
        this.snackbar.color = 'success'
        this.getMatriculation()
      } catch (error) {
        this.snackbar.text = 'Erro ao processar: ' + error
        this.snackbar.show = true
      }
    },
    async getMatriculation () {
      if (this.matriculation.identifier) {
        const search = {
          example: {
            identifier: this.matriculation.identifier
          }
        } as HttpSearchRequest
        const response = await httpAPI.post('/matriculation/search', search)
        this.matriculation = response.data.data.result[0]
        await this.getBillToReceiveList()

        if (this.matriculation.matriculationItemList.length <= 0) {
          this.disablePayment = true
        }
      }
    },
    async getBillToReceiveList () {
      if (this.matriculation.identifier) {
        const search = {
          example: {
            matriculation: {
              identifier: this.matriculation.identifier
            }
          },
          pageable: false
        } as HttpSearchRequest
        const response = await httpAPI.post('/bill-to-receive/search', search)
        this.billToReceiveList = response.data.data.result
        this.proccessFinancial()
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
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/state/search', search)
      this.states = response.data.data.result
    },
    async getCountries () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/country/search', search)
      this.countries = response.data.data.result
    },
    async getProducts () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/product/search', search)
      this.productList = response.data.data.result
    },
    async getGrateItemList (grateId: any) {
      const search = {
        example: {
          grate: {
            identifier: grateId
          }
        },
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/grate-item/search', search)
      return response.data.data.result
    },
    async getClasses () {
      const search = {
        example: {},
        pageable: false
      } as HttpSearchRequest
      const response = await httpAPI.post('/class/search', search)
      this.classList = response.data.data.result
    }
  },
  created () {
    this.getCountries()
    this.getStates()
    this.getCities()
    this.getProducts()
    this.getClasses()
    if (this.matriculation.identifier) {
      this.getMatriculation()
    }
  }
})
</script>

<style>
.v-stepper,
.v-stepper__wrapper,
.v-data-table__wrapper,
.v-stepper__items {
  overflow: visible;
}
.v-menu__content .theme--light .menuable__content__active {
  position: initial !important;
}
.v-input__slot{
  height: 32px;
}
</style>
