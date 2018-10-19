import Vue from 'vue'
import Vuex from 'vuex'

import gists from './modules/gists/index'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    gists
  }
})
