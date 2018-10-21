<template>
  <div class="menu-bar">

    <router-link :to="'/users/'+username">
      <div class="card menu">
        <label>Logout</label>
      </div>
    </router-link>

    <router-link :to="'/users/'+username">
      <div class="card menu">
        <label>Your Gists</label>
      </div>
    </router-link>

  </div>
</template>

<script>

export default {
  props: {
    username: String
  }
}

</script>

<style scoped>
.menu-bar {
  text-align: center;
}
.menu {
  text-align: center;
  width: 30%;
  margin: 16px auto auto auto;
  color: black;
  display: inline-block;
}
</style>
