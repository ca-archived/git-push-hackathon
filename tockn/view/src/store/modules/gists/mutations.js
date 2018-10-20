export default {
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
  },
  initGists (state) {
    state.gists = undefined
  },
  initGist (state) {
    state.gist = undefined
  }
}
