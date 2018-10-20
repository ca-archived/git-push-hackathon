<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
body {
  background-color: #f5f5f5;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin-top: 60px;
}

.card {
  box-shadow:0px 3px 3px 0px rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  background-color: #ffffff;
  margin-top: 15px;
  margin-bottom: 15px;
  padding: 10px 28px;
}

.card .title {
  font-size: 24px;
}

.card .content {
  font-size: 16px;
}

</style>
