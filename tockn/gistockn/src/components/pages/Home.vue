<template>
  <button @click="login">ログイン</button>
</template>

<script>

export default {
  methods: {
    login () {
      location.href = 'https://github.com/login/oauth/authorize?client_id=3d7e5111a3d44cd0fd6b'
    }
  }
}

</script>
