import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

import gists from './modules/gists/index'
import users from './modules/users/index'
import auth from './modules/auth/index'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    gists,
    users,
    auth
  },
  plugins: [createPersistedState({
    key: 'git-push-hackathon',
    paths: ['auth.at']
  })]
})
