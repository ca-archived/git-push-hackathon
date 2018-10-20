export default {
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
