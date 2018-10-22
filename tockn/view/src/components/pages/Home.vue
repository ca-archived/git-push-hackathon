<template>
  <div>
    <button @click="login">ログイン</button>
    <input v-model="username">
    <router-link :to="`/users/${username}`">User</router-link>
  </div>
</template>

<script>
const endpoint = process.env.OAUTH_ENDPOINT

export default {
  data () {
    return {
      username: ''
    }
  },
  methods: {
    login () {
      location.href = `${endpoint}/auth/github`
    }
  }
}

</script>
