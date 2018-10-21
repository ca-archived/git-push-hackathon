import axios from 'axios'
const API_ENDPOINT = process.env.API_ENDPOINT

export default {
  getUser ({ commit, rootState }, username) {
    commit('initUser')
    let req = request('GET', `${API_ENDPOINT}/users/${username}`, rootState.auth.at)
    axios(req)
      .then(response => {
        commit('getUser', response)
      })
  },
  searchUser ({ commit, rootState }, username) {
    commit('setSearching', true)
    let req = request('GET', `${API_ENDPOINT}/users/${username}`, rootState.auth.at)
    req.validateStatus = status => { return status < 500 }
    axios(req)
      .then(response => {
        commit('searchUser', response)
        commit('setSearching', false)
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
