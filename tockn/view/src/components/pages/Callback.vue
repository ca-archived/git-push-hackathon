<template>
  <div></div>
</template>

<script>

import Store from '../../store/index'

export default {
  created () {
    Store.commit('setAccessToken', this.$route.query.access_token)
    this.$router.push('/')
  }
}

</script>
