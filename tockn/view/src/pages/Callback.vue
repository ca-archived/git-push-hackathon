<template>
  <div></div>
</template>

<script>

export default {
  created () {
    this.$store.commit('gists/setAccessToken', this.$route.query.access_token)
    let path = this.$route.query.path
    if (path === '') {
      path = `/${path}`
    }
    this.$router.push(path)
  }
}

</script>
