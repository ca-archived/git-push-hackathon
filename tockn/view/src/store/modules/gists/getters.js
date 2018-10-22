export default {
  gists: (state) => {
    if (state.gistsPage === 0) {
      return false
    }
    return state.gists
  },
  gist: (state) => {
    return state.gist || false
  }
}
