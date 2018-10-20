<template>
  <div>
    <div v-if="gists">
      <div class="card" v-for="(gist, index) in gists" :key="index" >
        <div class="title">
          {{ cutDesc(gist.description) }}
        </div>
        <div class="content">
          <p v-for="(name, index) in fileNames(gist.files)" :key="index">{{ name }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Card from './GistCard'

export default {
  props: {
    gists: Object
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
    fileNames (files) {
      let names = Object.keys(files)
      if (names.length > 10) {
        names = names.slice(0, 10)
        names.push('...and more!')
      }
      return names
    }
  },
  components: {
    'card': Card
  }
}
</script>
