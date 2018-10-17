import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

const API_ENDPOINT = process.env.API_ENDPOINT

Vue.use(Vuex)

var Store = new Vuex.Store({
  state: {
    // 認可したユーザー情報
    me: undefined,

    gist: undefined,
    gists: undefined,

    // アクセストークン
    at: ''
  },
  mutations: {
    setAccessToken (state, at) {
      state.at = at
    },
    getMyData (state, response) {
      state.me = response.data
    },
    getGists (state, response) {
      state.gists = response.data
    },
    getGist (state, response) {
      state.gist = response.data
    }
  },
  actions: {
    getMyData ({ commit, state }) {
      if (state.at === '') throw new Error('not set access token')
      axios({
        method: 'GET',
        url: `${API_ENDPOINT}/user`,
        headers: {'Authorization': `bearer ${state.at}`}
      })
        .then(response => {
          commit('getMyData', response)
        })
    },
    getGists ({ commit, state }, username) {
      axios({
        method: 'GET',
        url: `${API_ENDPOINT}/users/${username}/gists`
      })
        .then(response => {
          commit('getGists', response)
        })
    },
    getMyGists ({ commit, state }) {
      axios({
        method: 'GET',
        url: `${API_ENDPOINT}/gists`,
        headers: {'Authorization': `bearer ${state.at}`}
      })
        .then(response => {
          commit('getGists', response)
        })
    },
    getGist ({ commit }, id) {
      axios({
        method: 'GET',
        url: `${API_ENDPOINT}/gists/${id}`
      })
        .then(response => {
          commit('getGist', response)
        })
    }
  }
})

export default Store
