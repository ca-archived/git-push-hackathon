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
    <page-btn v-show="!loading" @click="getUserGists(username)"/>
  </div>
</template>

<script>
import {mapState, mapGetters, mapActions} from 'vuex'
import Loading from '../parts/Loading'
import UserCard from '../parts/UserCard'
import GistCard from '../parts/GistCard'
import PageButton from '../parts/PageButton'

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
    this.initPage().then(() => {
      this.getUser(this.username)
      this.getUserGists(this.username)
    })
  },
  components: {
    'loading': Loading,
    'user-card': UserCard,
    'gist-card': GistCard,
    'page-btn': PageButton
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
