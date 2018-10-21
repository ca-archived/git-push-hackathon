export default {
  init (state) {
    state.me = undefined
    state.at = ''
  },
  setAccessToken (state, at) {
    state.at = at
  },
  getMyData (state, response) {
    state.me = response.data
  }
}
