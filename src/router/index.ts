import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import PanelView from '../views/PanelView.vue'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import Panel2View from '@/views/Panel2View.vue'
import EmployeeListView from '@/views/employee/EmployeeListView.vue'
import EmployeeFormView from '@/views/employee/EmployeeFormView.vue'
import ClassroomFormView from '@/views/classroom/ClassroomFormView.vue'
import ClassroomListView from '@/views/classroom/ClassroomListView.vue'
import NoticeFormView from '@/views/notice/NoticeFormView.vue'
import NoticeListView from '@/views/notice/NoticeListView.vue'
import PaymentMethodFormView from '@/views/payment-method/PaymentMethodFormView.vue'
import PaymentMethodListView from '@/views/payment-method/PaymentMethodListView.vue'
import PaymentConditionFormView from '@/views/payment-condition/PaymentConditionFormView.vue'
import PaymentConditionListView from '@/views/payment-condition/PaymentConditionListView.vue'
import SupplierFormView from '@/views/supplier/SupplierFormView.vue'
import SupplierListView from '@/views/supplier/SupplierListView.vue'
import GrateFormView from '@/views/product/grate/GrateFormView.vue'
import GrateListView from '@/views/product/grate/GrateListView.vue'
import ProductFormView from '@/views/product/product/ProductFormView.vue'
import ProductListView from '@/views/product/product/ProductListView.vue'
import ClassFormView from '@/views/class/ClassFormView.vue'
import ClassListView from '@/views/class/ClassListView.vue'
import MatriculationFormView from '@/views/matriculation/MatriculationFormView.vue'
import MatriculationListView from '@/views/matriculation/MatriculationListView.vue'
import SaleFormView from '@/views/sale/SaleFormView.vue'
import BillToPayFormView from '@/views/financial/bill-to-pay/BillToPayFormView.vue'
import BillToPayListView from '@/views/financial/bill-to-pay/BillToPayListView.vue'
import BillToReceiveFormView from '@/views/financial/bill-to-receive/BillToReceiveFormView.vue'
import BillToReceiveListView from '@/views/financial/bill-to-receive/BillToReceiveListView.vue'
import PriceTableFormView from '@/views/product/price-table/PriceTableFormView.vue'
import PriceTableListView from '@/views/product/price-table/PriceTableListView.vue'
import PayrollFormView from '@/views/financial/bill-to-pay/payroll/PayrollFormView.vue'
import PayrollListView from '@/views/financial/bill-to-pay/payroll/PayrollListView.vue'
import BillToPayReportView from '@/views/financial/bill-to-pay/report/BillToPayReportView.vue'
import BillToReceiveReportView from '@/views/financial/bill-to-receive/report/BillToReceiveReportView.vue'
import IndexView from '@/views/Index.vue'
import { lsUserDetailsKey } from '@/store/global'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'root',
    component: LoginView
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView
  },
  {
    path: '/home',
    name: 'home',
    component: PanelView
  },
  {
    path: '/panel',
    name: '',
    component: IndexView,
    children: [
      {
        path: '',
        name: 'Painel administrativo | Studio Jana Brun | Dashboard',
        component: HomeView
      },
      {
        path: '/panel/register/person/employee',
        name: 'Painel administrativo | Studio Jana Brun | Funcionários',
        component: EmployeeListView
      },
      {
        path: '/panel/register/person/employee/edit',
        name: 'Painel administrativo | Studio Jana Brun | Novo funcionário',
        component: EmployeeFormView
      },
      {
        path: '/panel/register/person/employee/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar funcionário',
        component: EmployeeFormView
      },
      {
        path: '/panel/register/classroom',
        name: 'Painel administrativo | Studio Jana Brun | Salas',
        component: ClassroomListView
      },
      {
        path: '/panel/register/classroom/edit',
        name: 'Painel administrativo | Studio Jana Brun | Nova sala',
        component: ClassroomFormView
      },
      {
        path: '/panel/register/classroom/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar sala',
        component: ClassroomFormView
      },
      {
        path: '/panel/register/notice',
        name: 'Painel administrativo | Studio Jana Brun | Avisos',
        component: NoticeListView
      },
      {
        path: '/panel/register/notice/edit',
        name: 'Painel administrativo | Studio Jana Brun | Novo aviso',
        component: NoticeFormView
      },
      {
        path: '/panel/register/notice/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar aviso',
        component: NoticeFormView
      },
      {
        path: '/panel/register/financial/payment-method',
        name: 'Painel administrativo | Studio Jana Brun | Forma de pagamento',
        component: PaymentMethodListView
      },
      {
        path: '/panel/register/financial/payment-method/edit',
        name: 'Painel administrativo | Studio Jana Brun | Nova forma de pagamento',
        component: PaymentMethodFormView
      },
      {
        path: '/panel/register/financial/payment-method/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Edtiar forma de pagamento',
        component: PaymentMethodFormView
      },
      {
        path: '/panel/register/financial/payment-condition',
        name: 'Painel administrativo | Studio Jana Brun | Condições de pagamento',
        component: PaymentConditionListView
      },
      {
        path: '/panel/register/financial/payment-condition/edit',
        name: 'Painel administrativo | Studio Jana Brun | Nova condição de pagamento',
        component: PaymentConditionFormView
      },
      {
        path: '/panel/register/financial/payment-condition/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar condição de pagamento',
        component: PaymentConditionFormView
      },
      {
        path: '/panel/register/person/supplier',
        name: 'Painel administrativo | Studio Jana Brun | Fornecedores',
        component: SupplierListView
      },
      {
        path: '/panel/register/person/supplier/edit',
        name: 'Painel administrativo | Studio Jana Brun | Novo fornecedor',
        component: SupplierFormView
      },
      {
        path: '/panel/register/person/supplier/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar fornecedor',
        component: SupplierFormView
      },
      {
        path: '/panel/register/product/grate',
        name: 'Painel administrativo | Studio Jana Brun | Grades',
        component: GrateListView
      },
      {
        path: '/panel/register/product/grate/edit',
        name: 'Painel administrativo | Studio Jana Brun | Nova grade',
        component: GrateFormView
      },
      {
        path: '/panel/register/product/grate/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar grade',
        component: GrateFormView
      },
      {
        path: '/panel/register/product/product',
        name: 'Painel administrativo | Studio Jana Brun | Produtos',
        component: ProductListView
      },
      {
        path: '/panel/register/product/product/edit',
        name: 'Painel administrativo | Studio Jana Brun | Novo produto',
        component: ProductFormView
      },
      {
        path: '/panel/register/product/product/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar produto',
        component: ProductFormView
      },
      {
        path: '/panel/register/class',
        name: 'Painel administrativo | Studio Jana Brun | Turmas',
        component: ClassListView
      },
      {
        path: '/panel/register/class/edit',
        name: 'Painel administrativo | Studio Jana Brun | Nova turma',
        component: ClassFormView
      },
      {
        path: '/panel/register/class/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar turma',
        component: ClassFormView
      },
      {
        path: '/panel/matriculation',
        name: 'Painel administrativo | Studio Jana Brun | Matriculas',
        component: MatriculationListView
      },
      {
        path: '/panel/matriculation/edit',
        name: 'Painel administrativo | Studio Jana Brun | Nova matrícula',
        component: MatriculationFormView
      },
      {
        path: '/panel/matriculation/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar matrícula',
        component: MatriculationFormView
      },
      {
        path: '/panel/sale',
        name: 'Nova Vendas',
        component: SaleFormView
      },
      {
        path: '/panel/sale/edit/:identifier',
        name: 'Editar Venda',
        component: SaleFormView
      },
      {
        path: '/panel/financial/bill-to-pay',
        name: 'Painel administrativo | Studio Jana Brun | Contas a Pagar',
        component: BillToPayListView
      },
      {
        path: '/panel/financial/bill-to-pay/edit',
        name: 'Painel administrativo | Studio Jana Brun | Novo Contas a Pagar',
        component: BillToPayFormView
      },
      {
        path: '/panel/financial/bill-to-pay/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar Contas a Pagar',
        component: BillToPayFormView
      },
      {
        path: '/panel/financial/bill-to-pay/report',
        name: 'Painel administrativo | Studio Jana Brun | Relatótios/Consultas contas a pagar',
        component: BillToPayReportView
      },
      {
        path: '/panel/financial/bill-to-receive',
        name: 'Painel administrativo | Studio Jana Brun | Contas a receber',
        component: BillToReceiveListView
      },
      {
        path: '/panel/financial/bill-to-receive/edit',
        name: 'Painel administrativo | Studio Jana Brun | Novo conta a receber',
        component: BillToReceiveFormView
      },
      {
        path: '/panel/financial/bill-to-receive/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar conta a receber',
        component: BillToReceiveFormView
      },
      {
        path: '/panel/financial/bill-to-receive/report',
        name: 'Painel administrativo | Studio Jana Brun | Relatótios/Consultas contas a receber',
        component: BillToReceiveReportView
      },
      {
        path: '/panel/register/product/price-table',
        name: 'Painel administrativo | Studio Jana Brun | Tabelas de preços',
        component: PriceTableListView
      },
      {
        path: '/panel/register/product/price-table/edit',
        name: 'Painel administrativo | Studio Jana Brun | Nova tabela de preço',
        component: PriceTableFormView
      },
      {
        path: '/panel/register/product/price-table/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar tabela de preço',
        component: PriceTableFormView
      },
      {
        path: '/panel/financial/bill-to-pay/payroll',
        name: 'Painel administrativo | Studio Jana Brun | Folhas de pagamento',
        component: PayrollListView
      },
      {
        path: '/panel/financial/bill-to-pay/payroll/edit',
        name: 'Painel administrativo | Studio Jana Brun | Nova folhas de pagamento',
        component: PayrollFormView
      },
      {
        path: '/panel/financial/bill-to-pay/payroll/edit/:identifier',
        name: 'Painel administrativo | Studio Jana Brun | Editar folhas de pagamento',
        component: PayrollFormView
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const jsonUserDetails = localStorage.getItem(lsUserDetailsKey) as string
  const userDetails = JSON.parse(jsonUserDetails)

  if (to.path === '/login') {
    if (userDetails?.token) {
      return next('/panel')
    }
    return next()
  }

  if (from.path === '/login') {
    if (!userDetails?.token) {
      return next('/login')
    }
    return next()
  }

  if (!userDetails?.token) {
    return next('/login')
  }
  return next()
})

export default router
