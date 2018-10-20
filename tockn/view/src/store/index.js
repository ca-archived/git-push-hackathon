import Vue from 'vue'
import Vuex from 'vuex'

import gists from './modules/gists/index'
import users from './modules/users/index'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    gists,
    users
  }
})
