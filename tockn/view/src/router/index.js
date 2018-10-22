import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/pages/Home'
import Callback from '@/components/pages/Callback'
import User from '@/components/pages/User'
import Gist from '@/components/pages/Gist'
import Logout from '@/components/pages/Logout'
import Create from '@/components/pages/CreateGist'
import Edit from '@/components/pages/EditGist'

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
    },
    {
      path: '/create',
      name: 'Create',
      component: Create
    },
    {
      path: '/gists/:id/edit',
      name: 'Edit',
      component: Edit
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    return {x: 0, y: 0}
  }
})
