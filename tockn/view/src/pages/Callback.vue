<template>
  <div></div>
</template>

<script>

export default {
  created () {
    this.$store.commit('gists/setAccessToken', this.$route.query.access_token)
    this.$router.push('/')
  }
}

</script>
