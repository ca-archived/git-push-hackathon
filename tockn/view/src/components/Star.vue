<template>
  <button class="btn">
    <div class="wrap">
      <img class="icon" src="../assets/star.png" alt="">
      <label>{{ state }}</label>
    </div>
  </button>
</template>

<script>

export default {
  props: {
    id: String
  },
  computed: {
    state () {
      if (this.$store.state.gists.starred) {
        return 'Unstar'
      } else {
        return 'Star'
      }
    }
  },
  created () {
    if (this.id !== undefined) {
      this.$store.dispatch('gists/checkStarred', this.id)
    }
  },
  watch: {
    id (value) {
      this.$store.dispatch('gists/checkStarred', value)
    }
  }
}

</script>

<style scoped>

.btn {
  border-radius: 2px;
  width: 80px;
  height: 32px;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
}

.icon {
  width: 16px;
  height: 16px;
  float: left;
}

</style>
