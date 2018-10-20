export default {
  setAccessToken (state, at) {
    state.at = at
  },
  getMyData (state, response) {
    state.me = response.data
  }
}
