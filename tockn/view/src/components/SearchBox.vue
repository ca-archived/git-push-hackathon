<template>
  <input v-model="text" @input="changeText" class="search-input" placeholder="ユーザーを検索">
</template>

<script>
export default {
  data () {
    return {
      text: '',
      timer: 0
    }
  },
  methods: {
    changeText () {
      clearTimeout(this.timer)
      this.timer = setTimeout(this.search.bind(this), 600)
    },
    search () {
      this.$emit('search', this.text)
    }
  }
}
</script>

<style scoped>
.search-input {
  width: 80%;
}
</style>
