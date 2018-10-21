<template>
  <button class="btn" @click="star">
    <div class="wrap">
      <img class="icon" src="../assets/star.png" alt="">
      <label>{{ state }}</label>
    </div>
  </button>
</template>

<script>
import {mapState, mapActions} from 'vuex'
export default {
  props: {
    id: String
  },
  computed: {
    ...mapState({
      starred: state => state.gists.starred
    }),
    state () {
      if (this.starred) {
        return 'Unstar'
      } else {
        return 'Star'
      }
    }
  },
  methods: {
    ...mapActions('gists', {
      putStar: 'putStar',
      deleteStar: 'deleteStar',
      checkStarred: 'checkStarred'
    }),
    star () {
      if (this.starred) {
        this.deleteStar(this.id)
      } else {
        this.putStar(this.id)
      }
    }
  },
  created () {
    if (this.id !== undefined) {
      this.checkStarred(this.id)
    }
  },
  watch: {
    id (value) {
      this.checkStarred(value)
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
