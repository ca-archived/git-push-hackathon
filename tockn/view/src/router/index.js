import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/pages/Home'
import Callback from '@/components/pages/Callback'
import User from '@/components/pages/User'
import Gist from '@/components/pages/Gist'

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
      path: '/gist/:id',
      name: 'Gist',
      component: Gist
    }
  ]
})