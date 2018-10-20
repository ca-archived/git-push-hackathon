<template>
  <div>
    <div v-if="gist" v-for="(file, index) in gist.files" :key="index">
      <pre>{{ file.content }}</pre>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'

export default {
  computed: {
    ...mapGetters('gists', {
      gist: 'gist'
    })
  },
  created () {
    this.$store.dispatch('gists/getGist', this.$route.params.id)
  }
}

</script>
