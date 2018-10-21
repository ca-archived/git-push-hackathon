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

    <div class="card table">
      <div class="add">
        <button class="add-btn" @click="addFile">Add file</button>
      </div>
      <loading v-show="loading"/>
      <div class="public" v-show="!loading">
        <button class="public-btn" @click="createGist(true)" >Create public gist</button>
        <button class="secret-btn" @click="createGist(false)">Create secret gist</button>
      </div>
      <p class="error">{{ message }}</p>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import Editor from '../components/Editor'
import Loading from '../components/Loading'

export default {
  data () {
    return {
      description: '',
      files: [{}],
      message: '',
      loading: false
    }
  },
  computed: {
    ...mapState({
      gist: state => state.gists.gist
    })
  },
  methods: {
    setFilename (index, filename) {
      this.files[index].filename = filename
    },
    setContent (index, content) {
      this.files[index].content = content
    },
    addFile () {
      this.files.push({})
    },
    validation () {
      for (let i = 0; i < this.files.length; i++) {
        let c = this.files[i].content
        if (c !== '' && c !== undefined) {
          this.message = ''
          return true
        }
      }
      this.message = 'Contents can\'t be empty'
      return false
    },
    createGist (public_) {
      if (!this.validation()) return
      this.loading = true
      let body = {}
      let files = {}
      let count = 1
      this.files.forEach(file => {
        let c = file.content
        if (c === '' || c === undefined) return
        if (file.filename !== undefined) {
          files[file.filename] = {content: c}
        } else {
          files[`gistfile${count}.txt`] = {content: c}
          count++
        }
      })
      body.files = files
      body.public = public_
      body.description = this.description
      this.$store.dispatch('gists/createGist', body)
    }
  },
  created () {
    this.$store.commit('gists/initGist')
  },
  watch: {
    'gist' (value) {
      if (value !== undefined) {
        this.$router.push(`/gists/${this.gist.id}`)
      }
    }
  },
  components: {
    'editor': Editor,
    'loading': Loading
  }
}

</script>

<style scoped>

button {
  font-size: 20px;
  height: 40px;
  border-radius: 8px;
}

.wrap {
  text-align: center;
}

.description {
  margin: auto;
  width: 100%;
  padding-left: 0;
  padding-right: 0;
}

@media screen and (min-width: 0px) {
  .add {
    margin: 0 0 32px 0;
  }
  .add-btn {
    background-color: #fff;
  }
  .secret {
    margin: 8px 0 8px 0;
  }
  .secret-btn {
    background-color: #fffacd;
    margin: 0 16px 0 16px;
  }
  .public-btn {
    background-color: #fff;
  }
}
@media screen and (min-width: 1200px) {
  .table {
    display: table;
    width: 100%;
    padding-left: 0;
    padding-right: 0;
  }
  .add {
    display: table-cell;
  }
  .add-btn {
    background-color: #fff;
  }
  .secret {
    display: table-cell;
    text-align: right;
  }
  .secret-btn {
    background-color: #fffacd;
    margin: 0 16px 0 16px;
  }
  .public {
    display: table-cell;
    text-align: right;
  }
  .public-btn {
    background-color: #fff;
  }
}

.error {
  color: red;
}

</style>
