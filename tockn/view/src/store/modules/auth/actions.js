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
  }
}
