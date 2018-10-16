import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

// const API_ENDPOINT = 'https://api.github.com'

Vue.use(Vuex)

var Store = new Vuex.Store({
  state: {
    // アクセストークン
    at: ''
  },
  mutations: {
    setAccessToken (state, at) {
      state.at = at
    }
  },
  actions: {
  }
})

export default Store
