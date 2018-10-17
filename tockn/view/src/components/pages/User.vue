<template>
  <div>
    <div v-if="gists">
      <div class="card" v-for="(gist, index) in gists" :key="index" >
        <div class="title">
          {{ cutDesc(gist.description) }}
        </div>
        <div class="content">
          <p>{{ fileName(gist.files) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from '../../store/index'
import Card from '../modules/GistCard'

export default {
  computed: {
    gists () {
      return store.state.gists || false
    }
  },
  methods: {
    cutDesc (text) {
      if (text === '') {
        return '-No Description-'
      }
      if (text.length > 100) {
        return text.slice(0, 100) + '...'
      }
      return text
    },
    fileName (files) {
      let names = Object.keys(files)
      if (names.length > 10) {
        names = names.slice(0, 10)
        names.push('...and more!')
      }
      return names.join(', ')
    }
  },
  created () {
    let username = this.$route.params.username
    if (store.state.me !== undefined &&
      store.state.me.login === username) {
      store.dispatch('getMyGists')
    } else {
      store.dispatch('getGists', username)
    }
  },
  components: {
    'card': Card
  }
}
</script>
