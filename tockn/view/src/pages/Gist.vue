<template>
  <div>
    <div v-if="gist" v-for="(file, index) in gist.files" :key="index">
      <div class="card">
        <b>{{ file.filename }}</b>
      </div>
      <div class="card no-padding">
      <prism v-if="langExist(file.language)" v-bind:language="file.language.toLowerCase()">{{ file.content }}</prism>
      <pre v-else>{{ file.content }}</pre>
      </div>
    </div>
    <loading v-else :withCard="true" />
  </div>
</template>

<script>
import PrismVue from 'vue-prism-component'
import Prism from '../../static/prism'
import Loading from '../components/Loading'
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
    langExist (language) {
      if (language === null) return
      return Prism.languages[language.toLowerCase()] || false
    }
  },
  components: {
    'prism': PrismVue,
    'loading': Loading
  }
}

</script>

<style>

.no-padding {
  padding: 0 0 0 0;
}

</style>
