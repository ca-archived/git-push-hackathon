<template>
  <div></div>
</template>

<script>
export default {
  created () {
    this.$store.commit('gists/init')
    this.$store.commit('users/init')
    this.$store.commit('auth/init')
    this.$router.push('/')
  }
}
</script>
