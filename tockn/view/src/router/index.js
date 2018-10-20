import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import Callback from '@/pages/Callback'
import User from '@/pages/User'
import Gist from '@/pages/Gist'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/auth/callback',
      name: 'Callback',
      component: Callback
    },
    {
      path: '/user/:username',
      name: 'User',
      component: User
    },
    {
      path: '/gists/:id',
      name: 'Gist',
      component: Gist
    }
  ]
})
