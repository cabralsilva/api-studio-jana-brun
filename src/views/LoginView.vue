<template>
  <v-app id="inspire">
    <v-main>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar dark color="primary">
                <v-toolbar-title>Studio Jana Brun - Acesso restrito</v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field v-model="credentials.username" prepend-icon="mdi-account" name="username" label="Email"
                    type="text"></v-text-field>
                  <v-text-field id="password" v-model="credentials.password" prepend-icon="mdi-form-textbox-password"
                    name="password" label="Password" type="password"></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="validLogin">Login</v-btn>
              </v-card-actions>
              <v-alert dismissible v-if="alert.length > 0" :key="this.$hashCode()" color="red" dark>{{ alert }}
              </v-alert>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-main>
    <app-snackbar/>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue'
import httpAPI from '@/plugins/axios'
import { lsUserDetailsKey } from '@/store/global'
import AppSnackbar from '@/components/AppSnackbar.vue'

export default Vue.extend({
  components: {
    AppSnackbar
  },
  name: 'LoginView',
  data () {
    return {
      credentials: {
        username: 'd.nadson@yahoo.com',
        password: '123456'
      },
      alert: ''
    }
  },
  props: {
    source: String
  },
  created () {
    const jsonUserDetails = localStorage.getItem(lsUserDetailsKey) as string
    const userDetails = JSON.parse(jsonUserDetails)
    if (userDetails?.token) {
      this.$router.push('/panel')
    }
  },
  methods: {
    isCredentialsEmpty () {
      if (
        this.credentials.username.length === 0 ||
        this.credentials.password.length === 0
      ) {
        return false
      }
      return true
    },
    async validLogin () {
      this.alert = ''
      if (this.isCredentialsEmpty()) {
        httpAPI.post('/employee/auth', this.credentials)
          .then(response => {
            localStorage.setItem(lsUserDetailsKey, JSON.stringify(response.data.data))
            this.$router.push('/panel').catch((_e) => console.log(_e))
          })
      } else {
        this.alert = 'Empty credentials'
      }
    }
  }
})
</script>

<style >
</style>
