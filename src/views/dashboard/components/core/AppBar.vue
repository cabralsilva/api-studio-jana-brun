<template>
  <v-app-bar id="app-bar" absolute app color="primary" flat height="75">
    <v-btn class="mr-3" elevation="1" fab small @click="setDrawer(!drawer)">
      <v-icon v-if="value">
        mdi-view-quilt
      </v-icon>

      <v-icon v-else>
        mdi-menu
      </v-icon>
    </v-btn>

    <v-toolbar-title class="hidden-sm-and-down" style="color: white" v-text="$route.name" />

    <v-spacer />

    <div class="mx-3" />

    <v-menu bottom offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn class="ml-2" dark icon v-bind="attrs" v-on="on" fab small>
          <v-icon>mdi-account</v-icon>
        </v-btn>
      </template>

      <v-list>
        <v-list-item @click="showFormChangePassword()">
          <v-list-item-title>Alterar senha</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    <change-password-dialog :key="changePasswordDialogKey" :callback="changePassword" :pShow="showChangePasswordDialog"
      :pTitle="'Mudar senha'">
      <template slot="inputForm">
        <v-text-field label="Senha atual" v-model="credentialsForm.password" :rules="[validator.required]"
          :append-icon="credentialsForm.showCurrentPassword ? 'mdi-eye' : 'mdi-eye-off'"
          :type="credentialsForm.showCurrentPassword ? 'text' : 'password'"
          @click:append="credentialsForm.showCurrentPassword = !credentialsForm.showCurrentPassword">
        </v-text-field>
        <v-text-field label="Nova senha" v-model="credentialsForm.newPassword" :rules="[validator.required]"
          :append-icon="credentialsForm.showNewPassword ? 'mdi-eye' : 'mdi-eye-off'"
          :type="credentialsForm.showNewPassword ? 'text' : 'password'" hint="No mínimo 8 caracteres" counter
          @click:append="credentialsForm.showNewPassword = !credentialsForm.showNewPassword">
        </v-text-field>
        <v-text-field label="Conf. nova senha" v-model="credentialsForm.confirmNewPassword"
          :rules="[validator.required, validConfirmationPassword]"
          :append-icon="credentialsForm.showConfirmNewPassword ? 'mdi-eye' : 'mdi-eye-off'"
          :type="credentialsForm.showConfirmNewPassword ? 'text' : 'password'" hint="No mínimo 8 caracteres" counter
          @click:append="credentialsForm.showConfirmNewPassword = !credentialsForm.showConfirmNewPassword">
        </v-text-field>
      </template>
    </change-password-dialog>
  </v-app-bar>
</template>

<script lang="ts">
import Vue from 'vue'
// Components
import ChangePasswordDialog from '@/components/FormDialog.vue'
// Utilities
import { mapState, mapMutations } from 'vuex'
import { Validations } from '@/lib/Utils'
import httpAPI from '@/plugins/axios'
import { lsUserDetailsKey } from '@/store/global'

export default Vue.extend({
  name: 'DashboardCoreAppBar',

  components: {
    ChangePasswordDialog
  },

  props: {
    value: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      validator: new Validations(),
      showChangePasswordDialog: false,
      changePasswordDialogKey: 0,
      credentialsForm: {
        identifier: null as any,
        password: null as any,
        newPassword: null as any,
        confirmNewPassword: null as any,
        showCurrentPassword: false,
        showNewPassword: false,
        showConfirmNewPassword: false
      }
    }
  },

  computed: {
    ...mapState(['drawer'])
  },

  methods: {
    resetCredentials () {
      this.credentialsForm = {
        identifier: null as any,
        password: null as any,
        newPassword: null as any,
        confirmNewPassword: null as any,
        showCurrentPassword: false,
        showNewPassword: false,
        showConfirmNewPassword: false
      }
    },
    showFormChangePassword () {
      this.showChangePasswordDialog = true
      this.changePasswordDialogKey++
    },
    async changePassword () {
      const jsonUserDetails = localStorage.getItem(lsUserDetailsKey) as string
      const userDetails = JSON.parse(jsonUserDetails)
      this.credentialsForm.identifier = userDetails.employee.identifier

      if (this.credentialsForm.identifier) {
        const response = await httpAPI.post('/employee/update-password', this.credentialsForm).then(() => this.resetCredentials())
      }
    },
    validConfirmationPassword () {
      if (this.credentialsForm.newPassword !== this.credentialsForm.confirmNewPassword) {
        return 'Confirmação inválida'
      }

      return true
    },

    ...mapMutations({
      setDrawer: 'SET_DRAWER'
    })
  }
})
</script>
