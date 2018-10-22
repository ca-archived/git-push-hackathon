<template>
  <div class="menu-bar">

    <router-link class="card menu" to="/logout">
        <label>Logout</label>
    </router-link>

    <router-link class="card menu" :to="'/users/'+username">
        <label>Your Gists</label>
    </router-link>

    <router-link class="card menu" to="/create">
        <label>New Gist</label>
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
  display: table;
  border-spacing: 8px;
  margin: auto;
}
.menu {
  text-align: center;
  vertical-align: middle;
  color: black;
  display: table-cell;
}
</style>
