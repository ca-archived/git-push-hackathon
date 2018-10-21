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
    <div class="card">
      <button class="add-btn">Add file</button>
      <button class="public-btn">Create public gist</button>
      <button class="secret-btn">Create secret gist</button>
    </div>
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

button {
  font-size: 20px;
  height: 40px;
  border-radius: 8px;
}

.add-btn {
  float: left;
}

.secret-btn {
  float: right;
  background-color: #fffacd;
  margin: 0 16px 0 16px;
}

.public-btn {
  float: right;
}

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
