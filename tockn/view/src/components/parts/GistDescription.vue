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
      <icon-btn class="star" :src="require('../../assets/star.png')" :text="starState" @click="$emit('star')" />
      <icon-btn v-if="isMyGist" :src="require('../../assets/pen.png')" text="edit" />
      <icon-btn v-if="isMyGist" :src="require('../../assets/stash.png')" text="delete" />
    </div>
  </div>
</template>

<script>
import Comments from './Comments'
import IconButton from './IconButton'

export default {
  props: {
    gist: Object,
    starred: Boolean,
    myname: String
  },
  computed: {
    starState () {
      if (this.starred) {
        return 'Unstar'
      } else {
        return 'Star'
      }
    },
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
    },
    isMyGist () {
      return this.owner.login === this.myname
    }
  },
  components: {
    'comments': Comments,
    'icon-btn': IconButton
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
