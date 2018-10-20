export default {
  getUser (state, response) {
    state.user = response.data
  },
  searchUser (state, response) {
    if (response.status !== 200) {
      state.searchResult = undefined
    } else {
      state.searchResult = response.data
    }
  },
  setSearching (state, search) {
    state.searching = search
  }
}
