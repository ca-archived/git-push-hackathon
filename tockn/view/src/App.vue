<template>
  <div id="app">
    <header- />
    <router-view class="body"/>
  </div>
</template>

<script>
import Header from './components/Header'
export default {
  name: 'App',
  components: {
    'header-': Header
  }
}
</script>

<style>
body {
  margin: 0 0 0 0;
  background-color: #f5f5f5;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

@media screen and (min-width: 0px) {
  .body {
    width: 98%;
    margin: auto;
  }
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
