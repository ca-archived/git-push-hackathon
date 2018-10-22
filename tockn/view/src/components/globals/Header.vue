<template>
  <div class="header">

    <div class="wrap">

      <div v-if="login">
        <div class="icon">
          <img class="icon-img" v-bind:src="avatarURL" width="50" height="50" @click="changeMenu">
        </div>
      </div>

      <div v-else >
        <a class="icon" v-bind:href="loginURL">
          <p>Login</p>
        </a>
      </div>

      <div class="search">
        <img @click="changeSearch" class="search-icon" src="../../assets/search.png" alt="search_logo.png">
      </div>

    </div>

    <div v-show="searchState" class="card search-box">
      <search-box @search="search" />
        <search-result :result="result" :searching="searching" />
    </div>

    <user-menu v-show="menuState" :username="login.login" />

  </div>
</template>

<script>
import {mapState} from 'vuex'
import SearchBox from '../parts/SearchBox'
import SearchResultBox from '../parts/SearchResultBox'
import Menu from '../parts/Menu'
const endpoint = process.env.OAUTH_ENDPOINT

export default {
  data () {
    return {
      searchState: false,
      menuState: false
    }
  },
  computed: {
    ...mapState({
      result: state => state.users.searchResult,
      searching: state => state.users.searching,
      avatarURL: state => state.auth.me.avatar_url,
      login: state => state.auth.me || false
    }),
    loginURL () {
      let path = this.$route.fullPath
      return `${endpoint}/auth/github?path=${path}`
    }
  },
  methods: {
    changeSearch () {
      if (this.menuState) this.menuState = false
      this.searchState = !this.searchState
    },
    search (text) {
      this.$store.dispatch('users/searchUser', text)
    },
    changeMenu () {
      if (this.searchState) this.searchState = false
      this.menuState = !this.menuState
    }
  },
  created () {
    if (this.$store.state.auth.at !== '') {
      this.$store.dispatch('auth/getMyData')
    }
  },
  components: {
    'search-box': SearchBox,
    'search-result': SearchResultBox,
    'user-menu': Menu
  },
  watch: {
    '$route' (to, from) {
      this.searchState = false
      this.menuState = false
    }
  }
}

</script>

<style scoped>

.header {
  overflow: hidden;
  background-color: #a52a2a;
  box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.2);
  padding: 5px 10px;
  margin-bottom: 15px;
}
.wrap {
  height: 50px;
}
.icon {
  float: right;
  width: 50px;
  height: 50px;
  font-size: 20px;
  color: white;
}
.icon-img {
  width: 50px;
  height: 50px;
  border-radius: 8px;
}
.search {
  float: left;
  width: 50px;
  height: 45px;
  padding-top: 5px;
}
.search-icon {
  width: 40px;
  height: 40px;
}
.search-box {
  text-align: center;
  margin: auto;
}
</style>
