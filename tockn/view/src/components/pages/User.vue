<template>
  <div>
    <div v-if="gists">
      <card v-for="(gist, index) in gists" :key="index" :title="gist.description" :content="'hoge'">
      <!--
        <div class="title">
          {{ gist.description }}
        </div>
        <div class="content">
          <p v-for="(file, index) in Object.keys(gist.files)" :key="index">
          {{ file }}
          </p>
        </div>
      -->
      </card>
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
