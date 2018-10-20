<template>
  <div v-if="user">
    <card :username="user.login" :avatarURL="user.avatar_url" />
  </div>
  <loading v-else />
</template>

<script>
import {mapGetters} from 'vuex'
import Loading from '../components/Loading'
import Card from '../components/UserCard'

export default {
  computed: {
    ...mapGetters('users', {
      user: 'user'
    })
  },
  created () {
    this.$store.dispatch('users/getUser', this.$route.params.username)
  },
  components: {
    'loading': Loading,
    'card': Card
  }
}

</script>
