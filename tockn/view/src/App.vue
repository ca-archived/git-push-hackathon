<template>
  <div id="app">
    <header- />
    <router-view class="body"/>
  </div>
</template>

<script>
import Header from './pages/Header'
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

input {
  font-size: 16px;
  border: 0;
  padding: 8px;
  border: solid 1px #ccc;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
  -moz-box-shadow: inset 0 0 4px rgba(0,0,0,0.2);
  -webkit-box-shadow: inset 0 0 4px rgba(0, 0, 0, 0.2);
  box-shadow: inner 0 0 4px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: inset 1px 4px 9px -6px rgba(0,0,0,0.5);
  -webkit-box-shadow: inset 1px 4px 9px -6px rgba(0, 0, 0, 0.5);
  box-shadow: inset 1px 4px 9px -6px rgba(0,0,0,0.5);
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
