<template>
  <button class="btn" @click="$emit('click')">
    <div class="wrap">
      <img class="icon" :src="src" alt="">
      <label>{{ text }}</label>
    </div>
  </button>
</template>

<script>
export default {
  props: {
    src: String,
    text: String
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
