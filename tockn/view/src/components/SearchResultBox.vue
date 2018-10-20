<template>
  <div class="card">
    <loading v-show="searching" />
    <div v-show="!searching">
      <div v-show="result === undefined">
        not exist.
      </div>
      <router-link :to="link" v-show="result !== undefined" class="link">
        <div class="avatar">
          <img class="avatar" v-bind:src="avatarURL" alt="">
        </div>
        <div class="username">
          {{ username }}
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
import Loading from './Loading'

export default {
  props: {
    result: Object,
    searching: Boolean
  },
  computed: {
    username () {
      if (this.result === undefined) return
      return this.result.login
    },
    avatarURL () {
      if (this.result === undefined) return
      return this.result.avatar_url
    },
    link () {
      return `/users/${this.username}`
    }
  },
  components: {
    'loading': Loading
  }
}

</script>

<style scoped>

.link {
  color: black
}

.avatar {
  width: 24px;
  height: 24px;
  display: inline-block;
  vertical-align: middle;
}

.username {
  margin: 0 4px 0 4px;
  display: inline-block;
  vertical-align: middle;
}

</style>
