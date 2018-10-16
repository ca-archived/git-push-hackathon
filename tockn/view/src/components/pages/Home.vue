<template>
  <button @click="login">ログイン</button>
</template>

<script>

const endpoint = process.env.OAUTH_ENDPOINT

export default {
  methods: {
    login () {
      location.href = `${endpoint}/auth/github?redirect_url=${location.href}auth/callback`
    }
  }
}

</script>
