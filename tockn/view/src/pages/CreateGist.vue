<template>
  <div class="wrap">
    <input class="description" v-model="description" placeholder="Gist description...">
    <editor
      v-for="(file, index) in files"
      :key="index"
      :index="index"
      :files="files"
      @setFilename="setFilename"
      @setContent="setContent" />
  </div>
</template>

<script>
import Editor from '../components/Editor'

export default {
  data () {
    return {
      description: '',
      files: [{}]
    }
  },
  methods: {
    setFilename (index, filename) {
      this.files[index].filename = filename
    },
    setContent (index, content) {
      this.files[index].content = content
    }
  },
  components: {
    'editor': Editor
  }
}

</script>

<style scoped>

.wrap {
  text-align: center;
}

.description {
  margin: auto;
  width: 98%;
}

.nopad {
  text-align: left;
  padding: 0 0 0 0;
}

.filename {
  background-color: #efefefef;
  margin: auto;
}

.filename .input {
  width: 40%;
}

</style>
