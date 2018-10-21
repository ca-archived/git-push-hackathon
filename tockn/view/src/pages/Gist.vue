<template>
  <div>
    <div v-if="gist">
      <description :gist="gist" :starred="starred" @star="star" />
      <div v-for="(file, index) in gist.files" :key="index">
        <div class="card">
          <b>{{ file.filename }}</b>
        </div>
        <div class="card no-padding">
          <prism v-if="langExist(file.language)" v-bind:language="file.language.toLowerCase()">{{ file.content }}</prism>
          <pre v-else>{{ file.content }}</pre>
        </div>
      </div>
    </div>
    <loading v-else :withCard="true" />
  </div>
</template>

<script>
import PrismVue from 'vue-prism-component'
import Prism from '../../static/prism'
import Loading from '../components/Loading'
import Desc from '../components/GistDescription'
import '../../static/prism.css'
import {mapGetters, mapState, mapActions} from 'vuex'

export default {
  computed: {
    ...mapState({
      starred: state => state.gists.starred
    }),
    ...mapGetters('gists', {
      gist: 'gist'
    })
  },
  methods: {
    ...mapActions('gists', {
      putStar: 'putStar',
      deleteStar: 'deleteStar',
      checkStarred: 'checkStarred'
    }),
    star () {
      if (this.starred) {
        this.deleteStar(this.gist.id)
      } else {
        this.putStar(this.gist.id)
      }
    },
    langExist (language) {
      if (language === null) return
      return Prism.languages[language.toLowerCase()] || false
    }
  },
  created () {
    this.$store.dispatch('gists/getGist', this.$route.params.id)
  },
  watch: {
    'gist' (value) {
      if (!this.gist) return
      this.checkStarred(this.gist.id)
    }
  },
  components: {
    'prism': PrismVue,
    'loading': Loading,
    'description': Desc
  }
}

</script>

<style>

.no-padding {
  padding: 0 0 0 0;
}

</style>
