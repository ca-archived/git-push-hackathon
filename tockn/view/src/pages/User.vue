<template>
  <div>
    <div v-if="user">
      <user-card :username="user.login" :avatarURL="user.avatar_url" />
    </div>
    <loading v-else :withCard="true" />
    <div v-if="gists">
      <gist-card v-for="(gist, index) in gists" :key="index" :gist="gist"/>
    </div>
    <loading v-else />
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import Loading from '../components/Loading'
import UserCard from '../components/UserCard'
import GistCard from '../components/GistCard'

export default {
  computed: {
    ...mapGetters('users', {
      user: 'user'
    }),
    ...mapGetters('gists', {
      gists: 'gists'
    })
  },
  methods: {
    getData () {
      this.$store.dispatch('users/getUser', this.$route.params.username)
      let username = this.$route.params.username
      if (this.$store.state.me !== undefined &&
        this.$store.state.me.login === username) {
        this.$store.dispatch('gists/getMyGists')
      } else {
        this.$store.dispatch('gists/getGists', username)
      }
    }
  },
  created () {
    this.getData()
  },
  components: {
    'loading': Loading,
    'user-card': UserCard,
    'gist-card': GistCard
  },
  watch: {
    '$route' (to, from) {
      this.$store.dispatch('users/getUser', this.$route.params.username)
      let username = this.$route.params.username
      if (this.$store.state.me !== undefined &&
        this.$store.state.me.login === username) {
        this.$store.dispatch('gists/getMyGists')
      } else {
        this.$store.dispatch('gists/getGists', username)
      }
    }
  }
}

</script>
