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
  },
  checkStarred (state, response) {
    if (response.status === 204) {
      state.starred = true
    } else {
      state.starred = false
    }
  },
  putStar (state, response) {
    if (response.status === 200) {
      state.starred = true
    }
  }
}
