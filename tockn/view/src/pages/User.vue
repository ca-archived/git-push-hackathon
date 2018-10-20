<template>
  <div>
    <div v-if="user">
      <card :username="user.login" :avatarURL="user.avatar_url" />
    </div>
    <loading v-else :withCard="true" />
    <div v-if="gists">
      <gists :gists="gists" />
    </div>
    <loading v-else :withCard="true" />
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import Loading from '../components/Loading'
import Card from '../components/UserCard'
import Gists from '../components/Gists'

export default {
  computed: {
    ...mapGetters('users', {
      user: 'user'
    }),
    ...mapGetters('gists', {
      gists: 'gists'
    })
  },
  created () {
    this.$store.dispatch('users/getUser', this.$route.params.username)
    let username = this.$route.params.username
    if (this.$store.state.me !== undefined &&
      this.$store.state.me.login === username) {
      this.$store.dispatch('gists/getMyGists')
    } else {
      this.$store.dispatch('gists/getGists', username)
    }
  },
  components: {
    'loading': Loading,
    'card': Card,
    'gists': Gists
  }
}

</script>
