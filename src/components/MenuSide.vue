<template>
  <v-navigation-drawer app permanent
    :style="{ top: $vuetify.application.top + 'px'}"
    :mini-variant.sync="mini">
    <v-list-item class="px-2">
      <v-list-item-avatar>
        <v-img src="https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned"></v-img>
      </v-list-item-avatar>

      <v-list-item-title>Alfredo Jaconi</v-list-item-title>

      <v-btn icon @click.stop="toggleMenuSide">
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
    </v-list-item>
    <v-divider></v-divider>
      <v-list>
        <template v-for="item in menuItems">
          <template v-if="item.items">
            <v-list-group :key="item.title" :prepend-icon="item.icon">
              <template v-slot:activator>
                <v-list-item-title v-text="item.title" />
              </template>
              <template v-for="child1 in item.items">
                <template v-if="child1.items">
                  <v-list-group :key="child1.title" no-action sub-group>
                    <template v-slot:activator>
                      <v-list-item-content>
                        <v-list-item-title v-text="child1.title" />
                      </v-list-item-content>
                    </template>

                    <v-list-item v-for="(child2, i) in child1.items" :key="i" link @click="navigation(child2.to)">
                      <v-list-item-title v-text="child2.title"></v-list-item-title>
                    </v-list-item>
                  </v-list-group>
                </template>
                <template v-else>
                  <v-list-item :key="child1.title" @click="navigation(child1.to)">
                    <v-list-item-title v-text="child1.title"  class="pl-14"/>
                  </v-list-item>
                </template>
              </template>
            </v-list-group>
          </template>
          <template v-else>
            <v-list-item :key="item.title" @click="navigation(item.to)">
              <v-list-item-icon>
                <v-icon v-text="item.icon" />
              </v-list-item-icon>
              <v-list-item-title v-text="item.title" />
            </v-list-item>
          </template>
        </template>
      </v-list>
    <v-divider></v-divider>
    <v-divider />
    <v-list-item link>
        <v-list-item-icon>
            <v-icon>mdi-logout</v-icon>
        </v-list-item-icon>
        <v-list-item-title>Logout</v-list-item-title>
    </v-list-item>
  </v-navigation-drawer>
</template>

<script lang="ts">
import Vue from 'vue'
import EventBus from '@/store/enventBus'

export default Vue.extend({
  name: 'DrawerSide',
  components: {
  },
  props: {
  },
  methods: {
    toggleMenuSide () {
      EventBus.toggleMenuSide(this.mini)
    },
    navigation (to: any) {
      if (to) {
        // eslint-disable-next-line
        this.$router.push(to).catch(err => { })
      }
    }
  },
  created () {
    EventBus.onToggleMenuSide((prop: any) => {
      this.mini = prop
      console.log(this.mini)
    })
  },
  data () {
    return {
      mini: true,
      menuItems: [
        {
          title: 'Home',
          active: true,
          icon: 'mdi-home',
          to: '/panel'
        },
        {
          title: 'Cadastros',
          icon: 'mdi-book-edit',
          items: [
            {
              title: 'Avisos',
              icon: 'mdi-sticker-text-outline',
              to: '/panel/register/notice'
            },
            {
              title: 'Financeiro',
              icon: 'mdi-sticker-text-outline',
              items: [
                {
                  title: 'Forma de pagamento',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/financial/payment-method'
                },
                {
                  title: 'Condição de pagamento',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/financial/payment-condition'
                }
              ]
            },
            {
              title: 'Pessoas/Empresas',
              icon: 'mdi-sticker-text-outline',
              items: [
                {
                  title: 'Fornecedores',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/person/supplier'
                },
                {
                  title: 'Funcionários',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/person/employee'
                }
              ]
            },
            {
              title: 'Produto',
              icon: 'mdi-sticker-text-outline',
              items: [
                {
                  title: 'Grade',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/product/grate'
                },
                {
                  title: 'Tabela de preço',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/product/price-table'
                },
                {
                  title: 'Produto',
                  icon: 'mdi-sticker-text-outline',
                  to: '/panel/register/product/product'
                }
              ]
            },
            {
              title: 'Salas',
              icon: 'mdi-sticker-text-outline',
              to: '/panel/register/classroom'
            },
            {
              title: 'Turmas',
              icon: 'mdi-sticker-text-outline',
              to: '/panel/register/class'
            }
          ]
        },
        {
          title: 'Financeiro',
          icon: 'mdi-finance',
          items: [
            {
              title: 'Contas a Pagar',
              icon: 'mdi-call-made',
              to: '/panel/financial/bill-to-pay'
            },
            {
              title: 'Contas a Receber',
              icon: 'mdi-call-received',
              to: '/panel/financial/bill-to-receive'
            }
          ]
        },
        {
          title: 'Matrícula',
          icon: 'mdi-book-edit',
          to: '/panel/matriculation'
        },
        {
          title: 'Vendas',
          icon: 'mdi-book-edit',
          to: '/panel/sale'
        }
      ]
    }
  }
})
</script>
