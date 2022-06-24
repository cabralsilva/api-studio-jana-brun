<template>
  <v-navigation-drawer id="core-navigation-drawer" v-model="drawer"
    :expand-on-hover="expandOnHover"
    :right="$vuetify.rtl" mobile-breakpoint="960" app width="260" v-bind="$attrs">

    <v-list dense nav class="primary" height="75">
      <v-list-item>
        <v-list-item-avatar>
          <v-img :src="'https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black'"></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title style="color: white"><h3>{{profile.person.nickName}}</h3></v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-divider class="mb-1" />

    <v-list expand nav>
      <template v-for="item in items">
        <template v-if="item.items">
          <v-list-group :disabled="!isAuthorized(item.roles)" :key="item.title" :prepend-icon="item.icon">
            <template v-slot:activator>
              <v-list-item-title :disabled="!item.active" v-text="item.title" />
            </template>
            <template v-for="child1 in item.items">
              <template v-if="child1.items">
                <v-list-group :disabled="!isAuthorized(child1.roles)" :key="child1.title" no-action sub-group>
                  <template v-slot:activator>
                    <v-list-item-content>
                      <v-list-item-title v-text="child1.title" class="pl-10"/>
                    </v-list-item-content>
                  </template>
                  <v-list-item v-for="(child2, i) in child1.items" :disabled="!isAuthorized(child2.roles)" :key="i" link @click="child2.active ? $router.push(child2.to) : {}">
                    <v-list-item-title v-text="child2.title" class="pl-14"/>
                  </v-list-item>
                </v-list-group>
              </template>
              <template v-else>
                <v-list-item :disabled="!isAuthorized(child1.roles)" :key="child1.title" @click="child1.active ? $router.push(child1.to) : {}">
                  <v-list-item-title v-text="child1.title" class="pl-10"/>
                </v-list-item>
              </template>
            </template>
          </v-list-group>
        </template>
        <template v-else>
          <v-list-item :disabled="!isAuthorized(item.roles)" :key="item.title" @click="item.active ? $router.push(item.to) : {}">
            <v-list-item-icon>
              <v-icon v-text="item.icon" />
            </v-list-item-icon>
            <v-list-item-title v-text="item.title" />
          </v-list-item>
        </template>
      </template>
    </v-list>

    <template v-slot:append>
      <v-list-item @click="logout()" link>
        <v-list-item-icon>
            <v-icon>mdi-logout</v-icon>
        </v-list-item-icon>
        <v-list-item-title>Logout</v-list-item-title>
      </v-list-item>
    </template>
  </v-navigation-drawer>
</template>

<script lang="ts">
import Vue from 'vue'
import { mapState } from 'vuex'
import { Navigation } from '@/lib/Utils'
import { lsUserDetailsKey } from '@/store/global'

export default Vue.extend({
  name: 'DashboardCoreDrawer',

  props: {
    expandOnHover: {
      type: Boolean,
      default: false
    }
  },

  data () {
    return {
      navigation: new Navigation(),
      items: [
        {
          title: 'Home',
          icon: 'mdi-home',
          to: '/panel',
          active: true
        },
        {
          title: 'Cadastros',
          icon: 'mdi-book-edit',
          active: true,
          items: [
            {
              title: 'Avisos',
              icon: 'mdi-sticker-text-outline',
              to: '/panel/register/notice',
              roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
              active: true
            },
            {
              title: 'Financeiro',
              icon: 'mdi-sticker-text-outline',
              roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
              active: true,
              items: [
                {
                  title: 'Forma de pagamento',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/financial/payment-method',
                  roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
                  active: true
                },
                {
                  title: 'Condição de pagamento',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/financial/payment-condition',
                  roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
                  active: true
                }
              ]
            },
            {
              title: 'Pessoas/Empresas',
              icon: 'mdi-sticker-text-outline',
              roles: ['ROLE_ADMIN'],
              active: true,
              items: [
                {
                  title: 'Fornecedores',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/person/supplier',
                  roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
                  active: true
                },
                {
                  title: 'Funcionários',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/person/employee',
                  roles: ['ROLE_ADMIN'],
                  active: true
                }
              ]
            },
            {
              title: 'Produto',
              icon: 'mdi-sticker-text-outline',
              roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
              active: true,
              items: [
                {
                  title: 'Grade',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/product/grate',
                  roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
                  active: true
                },
                {
                  title: 'Tabela de preço',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/product/price-table',
                  roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
                  active: true
                },
                {
                  title: 'Produto',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/product/product',
                  roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
                  active: true
                }
              ]
            },
            {
              title: 'Salas',
              icon: 'mdi-sticker-text-outline',
              to: '/panel/register/classroom',
              roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
              active: true
            },
            {
              title: 'Turmas',
              icon: 'mdi-sticker-text-outline',
              to: '/panel/register/class',
              roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
              active: true
            }
          ]
        },
        {
          title: 'Financeiro',
          icon: 'mdi-finance',
          roles: ['ROLE_ADMIN'],
          active: true,
          items: [
            {
              title: 'Contas a Pagar',
              icon: 'mdi-call-made',
              roles: ['ROLE_ADMIN'],
              active: true,
              items: [
                {
                  title: 'Contas a Pagar',
                  icon: 'mdi-call-made',
                  to: '/panel/financial/bill-to-pay',
                  roles: ['ROLE_ADMIN'],
                  active: true
                },
                {
                  title: 'Folhas de pagamento',
                  icon: 'mdi-call-made',
                  to: '/panel/financial/bill-to-pay/payroll',
                  roles: ['ROLE_ADMIN'],
                  active: true
                },
                {
                  title: 'Relatório',
                  icon: 'mdi-call-made',
                  to: '/panel/financial/bill-to-pay/report',
                  roles: ['ROLE_ADMIN'],
                  active: true
                }
              ]
            },
            {
              title: 'Contas a Receber',
              icon: 'mdi-call-received',
              roles: ['ROLE_ADMIN'],
              active: true,
              items: [
                {
                  title: 'Contas a Receber',
                  to: '/panel/financial/bill-to-receive',
                  roles: ['ROLE_ADMIN'],
                  active: true
                },
                {
                  title: 'Relatório',
                  icon: 'mdi-call-made',
                  to: '/panel/financial/bill-to-receive/report',
                  roles: ['ROLE_ADMIN'],
                  active: true
                }
              ]
            },
            {
              title: 'Análise/Gráficos*',
              icon: 'mdi-call-made',
              roles: ['ROLE_ADMIN'],
              items: [
                {
                  title: 'À receber X À pagar*',
                  to: '/panel/financial/analyzer/crossover',
                  roles: ['ROLE_ADMIN'],
                  active: false
                },
                {
                  title: 'Histórico*',
                  icon: 'mdi-call-made',
                  to: '/panel/financial/analyzer/history',
                  roles: ['ROLE_ADMIN'],
                  active: false
                }
              ]
            }
          ]
        },
        {
          title: 'Matrícula',
          icon: 'mdi-account-details',
          to: '/panel/matriculation',
          roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
          active: true
        },
        {
          title: 'Vendas',
          icon: 'mdi-store',
          to: '/panel/sale',
          roles: ['ROLE_ADMIN', 'ROLE_BASIC'],
          active: true
        }
      ] as any
    }
  },

  computed: {
    ...mapState(['barColor', 'barImage']),
    drawer: {
      get () {
        return this.$store.state.drawer
      },
      set (val) {
        this.$store.commit('SET_DRAWER', val)
      }
    },
    profile () {
      const jsonUserDetails = localStorage.getItem(lsUserDetailsKey) as string
      const userDetails = JSON.parse(jsonUserDetails)
      return userDetails?.employee
    },
    userDetails () {
      const jsonUserDetails = localStorage.getItem(lsUserDetailsKey) as string
      const userDetails = JSON.parse(jsonUserDetails)
      return userDetails
    }
  },

  methods: {
    isAuthorized (roles: Array<string>) {
      const jsonUserDetails = localStorage.getItem(lsUserDetailsKey) as string
      const userDetails = JSON.parse(jsonUserDetails)
      if (roles) {
        const authorities = userDetails.authorities
        return authorities.map((i: any) => i.authority).some((r: any) => roles.includes(r))
      }
      return true
    },
    logout () {
      localStorage.removeItem(lsUserDetailsKey)
      this.$router.push('/login').catch((_e) => console.log(_e))
    }
  }
})
</script>

<style lang="sass">
  @import '~vuetify/src/styles/tools/_rtl.sass'

  #core-navigation-drawer
    .v-list-group__header.v-list-item--active:before
      opacity: .24

    .v-list-item
      &__icon--text,
      &__icon:first-child
        justify-content: center
        text-align: center
        width: 20px

        +ltr()
          margin-right: 24px
          margin-left: 12px !important

        +rtl()
          margin-left: 24px
          margin-right: 12px !important

    .v-list--dense
      .v-list-item
        &__icon--text,
        &__icon:first-child
          margin-top: 10px

    .v-list-group--sub-group
      .v-list-item
        +ltr()
          padding-left: 8px

        +rtl()
          padding-right: 8px

      .v-list-group__header
        +ltr()
          padding-right: 0

        +rtl()
          padding-right: 0

        .v-list-item__icon--text
          margin-top: 19px
          order: 0

        .v-list-group__header__prepend-icon
          order: 2

          +ltr()
            margin-right: 8px

          +rtl()
            margin-left: 8px
</style>
