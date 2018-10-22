<template>
  <div>
    <div v-if="user">
      <user-card :username="user.login" :avatarURL="user.avatar_url" />
    </div>
    <loading v-else :withCard="true" />
    <div v-if="gists">
      <gist-card v-for="(gist, index) in gists" :key="index" :gist="gist"/>
    </div>
    <loading v-show="loading" />
  </div>
</template>

<script>
import {mapState, mapGetters, mapActions} from 'vuex'
import Loading from '../parts/Loading'
import UserCard from '../parts/UserCard'
import GistCard from '../parts/GistCard'

export default {
  computed: {
    ...mapGetters('users', {
      user: 'user'
    }),
    ...mapGetters('gists', {
      gists: 'gists'
    }),
    ...mapState({
      loading: state => state.gists.loading
    }),
    username () {
      return this.$route.params.username
    }
  },
  methods: {
    ...mapActions('gists', [
      'getUserGists',
      'initPage'
    ]),
    ...mapActions('users', [
      'getUser'
    ]),
    getData () {
      this.getUser(this.username)
      this.getUserGists(this.username)
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
      this.initPage().then(() => {
        this.getUser(this.username)
        this.getUserGists(this.username)
      })
    }
  }
}

</script>
