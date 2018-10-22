<template>
  <router-link class="a" v-bind:to="`/gists/${gist.id}`" >
    <div class="card">

      <div class="title">
        <p>{{ cutDesc(gist.description) }}</p>
      </div>

      <div class="content">
        <p v-for="(name, index) in fileNames(gist.files)" :key="index">{{ name }}</p>
      </div>

      <div class="comments">
        <comments :num="gist.comments" />
      </div>

      <router-link :to="`/users/${gist.owner.login}`" class="link">
        <div class="avatar">
          <img class="avatar" :src="gist.owner.avatar_url" alt="">
        </div>
        <div class="username">
          {{ gist.owner.login }}
        </div>
      </router-link>

    </div>
  </router-link>
</template>

<script>
import Comments from './Comments'

export default {
  props: {
    gist: Object
  },
  methods: {
    cutDesc (text) {
      if (text === '' || text === null) {
        return '-No Description-'
      }
      if (text.length > 100) {
        return text.slice(0, 100) + '...'
      }
      return text
    },
    fileNames (files) {
      let names = Object.keys(files)
      if (names.length > 10) {
        names = names.slice(0, 10)
        names.push('...and more!')
      }
      return names
    }
  },
  components: {
    'comments': Comments
  }
}

</script>

<style scoped>
.a {
  text-decoration: none;
  color: black;
}

.comments {
  float: right;
}

.link {
  color: black;
  border-radius: 8px;
  padding: 4px 8px;
  border: solid 1px #000;
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
