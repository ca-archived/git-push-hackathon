<template>
  <div class="card">
    <button @click="$emit('click')">More</button>
  </div>
</template>

<style scoped>

.card {
  width: 60%;
  text-align: center;
  margin: 16px auto 16px auto;
}

button {
  width: 120px;
  height: 60px;
  font-size: 20px;
  border-radius: 8px;
}

</style>
