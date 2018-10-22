<template>
  <div class="menu-bar">

    <div class="card menu" @click="$router.push('/logout')">
        <label>Logout</label>
    </div>

    <div class="card menu" @click="$router.push('/users/'+username)">
        <label>Your Gists</label>
    </div>

    <div class="card menu" @click="$router.push('/create')">
        <label>New Gist</label>
    </div>

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
  padding: 10px 28px 10px 28px;
}
</style>
