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
          <img class="icon_img" v-bind:src="avatarURL" width="50" height="50">
        </router-link>
      </div>
      <div v-show="login" class="hid_login"></div>
      <div class="hid_search"></div>
    </div>
  </div>
</template>

<script>
const endpoint = process.env.OAUTH_ENDPOINT

export default {
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
.icon_img {
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
.search_icon {
  width: 40px;
  height: 40px;
}
</style>
