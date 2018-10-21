<template>
  <div class="card">
    <div class="avatar">
      <img class="avatar" v-bind:src="owner.avatar_url" />
    </div>
    <div class="username">
      <h2>{{ owner.login }}/{{ title }}</h2>
    </div>
    <comments class="comments" :num="comments"/>
  </div>
</template>

<script>
import Comments from './Comments'

export default {
  props: {
    gist: Object
  },
  computed: {
    owner () {
      if (!this.gist) return ''
      return this.gist.owner
    },
    title () {
      if (!this.gist) return ''
      return Object.keys(this.gist.files)[0]
    },
    comments () {
      if (!this.gist) return 0
      return this.gist.comments
    }
  },
  components: {
    'comments': Comments
  }
}

</script>

<style scoped>

.avatar {
  width: 72px;
  height: 72px;
  display: inline-block;
  vertical-align: middle;
  border-radius: 8px;
}

.username {
  margin: 0 16px 0 16px;
  display: inline-block;
  vertical-align: middle;
}

.comments {
  float: right;
}

</style>
