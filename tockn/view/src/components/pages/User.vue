<template>
  <div>
    {{ gists }}
  </div>
</template>

<script>
import store from '../../store/index'
import axios from 'axios'

const API_ENDPOINT = process.env.API_ENDPOINT

export default {
  data () {
    return {
      gists: {}
    }
  },
  created () {
    let username = this.$route.params.username
    if (store.state.me !== undefined &&
      store.state.me.login === username) {
      axios({
        method: 'GET',
        url: `${API_ENDPOINT}/gists`,
        headers: {'Authorization': `bearer ${store.state.at}`}
      })
        .then(response => {
          this.gists = response.data
        })
    } else {
      axios({
        method: 'GET',
        url: `${API_ENDPOINT}/users/${username}/gists`
      })
        .then(response => {
          this.gists = response.data
        })
    }
  }
}
</script>
