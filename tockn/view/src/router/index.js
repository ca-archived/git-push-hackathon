import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import Callback from '@/pages/Callback'
import User from '@/pages/User'
import Gist from '@/pages/Gist'
import Logout from '@/pages/Logout'

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
      path: '/users/:username',
      name: 'User',
      component: User
    },
    {
      path: '/gists/:id',
      name: 'Gist',
      component: Gist
    },
    {
      path: '/logout',
      name: 'Logout',
      component: Logout
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    return {x: 0, y: 0}
  }
})
