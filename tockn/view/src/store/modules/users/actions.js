import axios from 'axios'
const API_ENDPOINT = process.env.API_ENDPOINT

export default {
  getUser ({ commit }, username) {
    axios({
      method: 'GET',
      url: `${API_ENDPOINT}/users/${username}`
    })
      .then(response => {
        commit('getUser', response)
      })
  }
}
