<template>
  <div>
    <div v-if="gist" v-for="(file, index) in gist.files" :key="index">
      <prism language="go">{{ file.content }}</prism>
    </div>
  </div>
</template>

<script>
import Prism from 'vue-prism-component'
import '../../static/prism'
import '../../static/prism.css'
import {mapGetters} from 'vuex'

export default {
  computed: {
    ...mapGetters('gists', {
      gist: 'gist'
    })
  },
  created () {
    this.$store.dispatch('gists/getGist', this.$route.params.id)
  },
  methods: {
  },
  components: {
    'prism': Prism
  }
}

</script>
