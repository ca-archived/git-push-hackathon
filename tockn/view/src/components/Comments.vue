<template>
  <div>
    <img class="comments-icon" src="../assets/comments.png" alt="">
    <p class="comments-num">{{ num }}</p>
  </div>
</template>

<script>

export default {
  props: {
    num: Number
  }
}

</script>

<style scoped>

.comments {
  float: right;
}

.comments-icon {
  width: 16px;
  height: 16px;
}

.comments-num {
  display: inline-block;
}

</style>
