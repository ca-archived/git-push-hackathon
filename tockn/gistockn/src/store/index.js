import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

var http = axios.create({withCredentials: true})

var Store = new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  }
}

export default Store
