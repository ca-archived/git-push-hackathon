<template>
  <div class="card search-box">
    <input v-model="text" @input="changeText" class="search-input" placeholder="ユーザーを検索">
    <loading v-show="searching" class="searching" />
    <div v-show="!searching">
      <div class="card result">
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
  </div>
</template>

<script>
import Loading from './Loading'

export default {
  data () {
    return {
      text: '',
      timer: 0
    }
  },
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
  methods: {
    changeText () {
      clearTimeout(this.timer)
      this.timer = setTimeout(this.search.bind(this), 500)
    },
    search () {
      this.$emit('search', this.text)
    }
  },
  components: {
    'loading': Loading
  }
}

</script>

<style scoped>

.result {
  width: 70%;
  margin: 16px auto 0 auto;
  padding: 8px auto;
}

.search-box {
  text-align: center;
  margin: auto;
  width: 40%;
}

@media screen and (min-width: 0px) {
  .search-box {
    width: 80%;
  }
}

@media screen and (min-width: 1200px) {
  .search-box {
    width: 800px;
  }
}

.search-input {
  width: 80%;
}

.link {
  color: black
}

.avatar {
  width: 32px;
  height: 32px;
  display: inline-block;
  vertical-align: middle;
}

.username {
  font-size: 16px;
  margin: 0 4px 0 4px;
  display: inline-block;
  vertical-align: middle;
}

</style>
