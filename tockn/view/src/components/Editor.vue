<template>
  <div class="card nopad">
    <div class="filename">
      <input class="input" v-model="filename" placeholder="Filename including extension...">
    </div>
    <codemirror v-model="content" :options="cmOptions"></codemirror>
  </div>
</template>

<script>
import {codemirror} from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'

export default {
  data () {
    return {
      cmOptions: {
        tabSize: 4,
        theme: 'base16-dark',
        lineNumbers: true,
        line: true
      }
    }
  },
  computed: {
    filename: {
      get () {
        return this.files[this.index].filename
      },
      set (value) {
        this.$emit('setFilename', this.index, value)
      }
    },
    content: {
      get () {
        return this.files[this.index].content
      },
      set (value) {
        this.$emit('setContent', this.index, value)
      }
    }
  },
  props: {
    files: Array,
    index: Number
  },
  methods: {
    setFilename () {
      this.$emit('setFilename', this.index, this.filename)
    },
    setContent () {
      this.$emit('setContent', this.index, this.content)
    }
  },
  components: {
    codemirror
  }
}

</script>

<style scoped>

@media screen and (min-width: 0px) {
  .filename .input {
    width: 80%;
  }
}
@media screen and (min-width: 1200px) {
  .filename .input {
    width: 560px;
  }

}
.nopad {
  text-align: left;
  padding: 0 0 0 0;
}

.filename {
  background-color: #efefefef;
  margin: auto;
}

</style>
