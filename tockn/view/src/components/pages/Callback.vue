<template>
  <div></div>
</template>

<script>

export default {
  created () {
    this.$store.dispatch('auth/setAccessToken', this.$route.query.access_token)
    this.$store.dispatch('auth/getMyData')
    let path = this.$route.query.path
    if (path === '') {
      path = `/${path}`
    }
    this.$router.push(path)
  }
}

</script>
