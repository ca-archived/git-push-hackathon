<template>
  <div class="header">
    <div class="wrap">
      <div v-if="!login">
        <div class="hid"></div>
        <a class="icon" v-bind:href="loginURL">
          <p>Login</p>
        </a>
      </div>
      <div v-if="login" class="icon">
        <router-link :to="'/'">
          <img class="icon-img" v-bind:src="avatarURL" width="50" height="50">
        </router-link>
      </div>
      <div v-show="login" class="hid_login"></div>
      <div class="hid_search"></div>
      <div class="search">
        <img @click="changeSearch" class="search-icon" src="../assets/search.png" alt="search_logo.png">
      </div>
    </div>
    <div v-show="searching" class="card search-box">
      <input class="search-input" placeholder="ユーザーを検索">
    </div>
  </div>
</template>

<script>
const endpoint = process.env.OAUTH_ENDPOINT

export default {
  data () {
    return {
      searching: false
    }
  },
  computed: {
    login () {
      return this.$store.state.auth.me || false
    },
    loginURL () {
      let path = this.$route.fullPath
      return `${endpoint}/auth/github?path=${path}`
    },
    avatarURL () {
      return this.$store.state.auth.me.avatar_url
    }
  },
  methods: {
    changeSearch () {
      this.searching = !this.searching
    }
  },
  created () {
    if (this.$store.state.auth.at !== '') {
      this.$store.dispatch('auth/getMyData')
    }
  }
}

</script>

<style>
a {
  color: black;
}
.header {
  overflow: hidden;
  background-color: #20b2aa;
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
.search-input {
  width: 80%;
}
</style>
