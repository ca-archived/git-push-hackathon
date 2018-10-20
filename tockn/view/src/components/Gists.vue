<template>
  <div>
    <div v-if="gists">
      <router-link class="a" v-bind:to="`/gists/${gist.id}`" v-for="(gist, index) in gists" :key="index" >
        <div class="card">
          <div class="title">
            <p>{{ cutDesc(gist.description) }}</p>
          </div>
          <div class="content">
            <p v-for="(name, index) in fileNames(gist.files)" :key="index">{{ name }}</p>
          </div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
import Card from './GistCard'

export default {
  props: {
    gists: Array
  },
  methods: {
    cutDesc (text) {
      if (text === '' || text === null) {
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

<style>
.a {
  text-decoration: none;
  color: black;
}
</style>
