<template>
  <div class="card">
    <div class="avatar">
      <img class="avatar" v-bind:src="owner.avatar_url" />
    </div>
    <div class="username">
      <h2>{{ owner.login }}/{{ title }}</h2>
    </div>
    <div class="info">
      <comments class="comments" :num="comments"/>
      <star class="star" :id="gist.id" />
    </div>
  </div>
</template>

<script>
import Comments from './Comments'
import Star from './Star'

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
    'comments': Comments,
    'star': Star
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

.info {
  display: inline-block;
  float: right;
}

.comments {
  display: inline-block;
  vertical-align: middle;
}

.star {
  display: inline-block;
  vertical-align: middle;
}

</style>
