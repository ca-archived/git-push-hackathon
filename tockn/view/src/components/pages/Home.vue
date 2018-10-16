<template>
  <button @click="login">ログイン</button>
</template>

<script>
import Store from '../../store/index'

const endpoint = process.env.OAUTH_ENDPOINT

export default {
  methods: {
    login () {
      location.href = `${endpoint}/auth/github?redirect_url=${location.href}auth/callback`
    }
  },
  mounted () {
    console.log(Store.state.at)
  }
}

</script>
