export default {
  init (state) {
    state.gist = undefined
    state.gists = undefined
    state.starred = false
    state.gistsPage = 0
    state.loading = false
  },
  getGists (state, response) {
    if (state.gistsPage === 1) {
      state.gists = response.data
    } else {
      Object.assign(state.gists, response.data)
    }
  },
  pageIncrement (state) {
    state.gistsPage++
  },
  initPage (state) {
    state.gistsPage = 0
  },
  getGist (state, response) {
    state.gist = response.data
  },
  setLoading (state, status) {
    state.loading = status
  },
  checkStarred (state, response) {
    if (response.status === 204) {
      state.starred = true
    } else {
      state.starred = false
    }
  },
  putStar (state, response) {
    if (response.status === 204) {
      state.starred = true
    }
  },
  deleteStar (state, response) {
    if (response.status === 204) {
      state.starred = false
    }
  },
  createGist (state, response) {
    state.gist = response.data
  }
}
