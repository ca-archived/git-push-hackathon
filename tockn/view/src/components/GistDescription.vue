<template>
  <div class="card">
    <div class="avatar">
      <img class="avatar" v-bind:src="avatarURL" />
    </div>
    <div class="username">
      <h2>{{ username }}/{{ title }}</h2>
    </div>
    <p>Comments: {{ comments }}</p>
  </div>
</template>

<script>

export default {
  props: {
    username: String,
    title: String,
    star: Number,
    fork: Number,
    comments: Number,
    avatarURL: String
  }
}

</script>

<style scoped>

.avatar {
  width: 72px;
  height: 72px;
  display: inline-block;
  vertical-align: middle;
  border-radius: 8px;
}

.username {
  margin: 0 16px 0 16px;
  display: inline-block;
  vertical-align: middle;
}

</style>
