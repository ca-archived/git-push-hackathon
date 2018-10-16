<template>
  <div>
    <button @click="login">ログイン</button>
    <input v-model="username">
    <router-link :to="`/user/${username}`">User</router-link>
  </div>
</template>

<script>
import Store from '../../store/index'

const endpoint = process.env.OAUTH_ENDPOINT

export default {
  data () {
    return {
      username: ''
    }
  },
  methods: {
    login () {
      location.href = `${endpoint}/auth/github?redirect_url=${location.href}auth/callback`
    }
  },
  created () {
    Store.dispatch('getMyData')
  }
}

</script>
