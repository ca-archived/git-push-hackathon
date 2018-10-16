import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

const API_ENDPOINT = process.env.API_ENDPOINT

Vue.use(Vuex)

var Store = new Vuex.Store({
  state: {
    // 認可したユーザー情報
    me: undefined,

    // アクセストークン
    at: ''
  },
  mutations: {
    setAccessToken (state, at) {
      state.at = at
    },
    getMyData (state, response) {
      state.me = response.data
    }
  },
  actions: {
    getMyData ({ commit, state }) {
      axios({
        method: 'GET',
        url: `${API_ENDPOINT}/user`,
        headers: {'Authorization': `bearer ${state.at}`}
      })
        .then(response => {
          commit('getMyData', response)
        })
    }
  }
})

export default Store
