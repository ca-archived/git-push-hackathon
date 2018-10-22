<template>
  <div class="wrap">
    <img class="icon" src="../../assets/comments.png" alt="">
    <p class="num">{{ num }}</p>
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

.wrap {
  margin: 0 8px 0 8px;
}

.icon {
  width: 16px;
  height: 16px;
}

.num {
  display: inline-block;
}

</style>
