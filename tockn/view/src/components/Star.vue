<template>
  <button class="btn">
    <div class="wrap">
      <img class="icon" src="../assets/star.png" alt="">
      <label>Unstar</label>
    </div>
  </button>
</template>

<script>

export default {
  props: {
    id: String
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
