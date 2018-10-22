import axios from 'axios'
import {request} from '../../utils'
const API_ENDPOINT = process.env.API_ENDPOINT

export default {
  getUserGists ({ commit, rootState }, username) {
    commit('initGists')
    let req = request('GET', `${API_ENDPOINT}/users/${username}/gists`, rootState.auth.at)
    axios(req)
      .then(response => {
        commit('getGists', response)
      })
  },
  getGist ({ commit, rootState }, id) {
    commit('initGist')
    let req = request('GET', `${API_ENDPOINT}/gists/${id}`, rootState.auth.at)
    axios(req)
      .then(response => {
        commit('getGist', response)
      })
  },
  checkStarred ({ commit, rootState }, id) {
    let req = request('GET', `${API_ENDPOINT}/gists/${id}/star`, rootState.auth.at)
    req.validateStatus = status => { return status === 204 || status === 404 }
    axios(req)
      .then(response => {
        commit('checkStarred', response)
      })
  },
  putStar ({ commit, rootState }, id) {
    let req = request('PUT', `${API_ENDPOINT}/gists/${id}/star`, rootState.auth.at)
    axios(req)
      .then(response => {
        commit('putStar', response)
      })
  },
  deleteStar ({ commit, rootState }, id) {
    let req = request('DELETE', `${API_ENDPOINT}/gists/${id}/star`, rootState.auth.at)
    axios(req)
      .then(response => {
        commit('deleteStar', response)
      })
  },
  createGist ({ commit, rootState }, body) {
    commit('initGist')
    let req = request('POST', `${API_ENDPOINT}/gists`, rootState.auth.at)
    req.data = body
    axios(req)
      .then(response => {
        commit('createGist', response)
      })
  }
}
