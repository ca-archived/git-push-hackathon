import axios from 'axios'
const API_ENDPOINT = process.env.API_ENDPOINT

export default {
  getGists ({ commit, state, rootState }, username) {
    commit('initGist')
    let req = request('GET', `${API_ENDPOINT}/users/${username}/gists`, rootState.auth.at)
    axios(req)
      .then(response => {
        commit('getGists', response)
      })
  },
  getMyGists ({ commit, state, rootState }) {
    commit('initGist')
    axios({
      method: 'GET',
      url: `${API_ENDPOINT}/gists`,
      headers: {'Authorization': `bearer ${rootState.auth.at}`}
    })
      .then(response => {
        commit('getGists', response)
      })
  },
  getGist ({ commit, state, rootState }, id) {
    commit('initGist')
    let req = request('GET', `${API_ENDPOINT}/gists/${id}`, rootState.auth.at)
    axios(req)
      .then(response => {
        commit('getGist', response)
      })
  }
}

var request = function (method, url, accessToken) {
  let reqObj = {
    method: method,
    url: url
  }
  if (accessToken !== '') {
    reqObj.headers = {'Authorization': `bearer ${accessToken}`}
  }
  return reqObj
}
