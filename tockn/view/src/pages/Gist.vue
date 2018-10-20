<template>
  <div>
    <div v-if="gist" v-for="(file, index) in gist.files" :key="index">
      <div class="card">
        {{ file.filename }}
      </div>
      <div class="card no-padding">
      <prism v-bind:language="file.language.toLowerCase()">{{ file.content }}</prism>
      </div>
    </div>
  </div>
</template>

<script>
import PrismVue from 'vue-prism-component'
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
    'prism': PrismVue
  }
}

</script>

<style>

.no-padding {
  padding: 0 0 0 0;
}

</style>
