<template>
  <div>
    <div v-if="gist" v-for="(file, index) in gist.files" :key="index">
      <pre>{{ file.content }}</pre>
    </div>
  </div>
</template>

<script>
import store from '../../store/index'

export default {
  computed: {
    gist () {
      return store.state.gist || false
    }
  },
  created () {
    store.dispatch('getGist', this.$route.params.id)
  }
}

</script>
