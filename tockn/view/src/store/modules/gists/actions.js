import axios from 'axios'
const API_ENDPOINT = process.env.API_ENDPOINT

export default {
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
    commit('initGist')
    let reqObj = {
      method: 'GET',
      url: `${API_ENDPOINT}/users/${username}/gists`
    }
    if (state.at !== '') {
      reqObj.headers = {'Authorization': `bearer ${state.at}`}
    }
    axios(reqObj)
      .then(response => {
        commit('getGists', response)
      })
  },
  getMyGists ({ commit, state }) {
    commit('initGist')
    axios({
      method: 'GET',
      url: `${API_ENDPOINT}/gists`,
      headers: {'Authorization': `bearer ${state.at}`}
    })
      .then(response => {
        commit('getGists', response)
      })
  },
  getGist ({ commit, state }, id) {
    commit('initGist')
    let reqObj = {
      method: 'GET',
      url: `${API_ENDPOINT}/gists/${id}`
    }
    if (state.at !== '') {
      reqObj.headers = {'Authorization': `bearer ${state.at}`}
    }
    axios(reqObj)
      .then(response => {
        commit('getGist', response)
      })
  }
}
