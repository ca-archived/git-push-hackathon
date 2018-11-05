'use strict'
import routes from '/routes.js'

Vue.prototype.token = localStorage.getItem('accessToken')

window.router = new VueRouter({
    'routes': routes,
    'mode': 'history',
    'scrollBehavior': (to, from, savedPosition) => {
        if (savedPosition) {
            return savedPosition
        } else {
            return { x: 0, y: 0 }
        }
    }
})

router.beforeEach((to, from, next) => {
    if (!('accessToken' in localStorage) && to.meta.requiresAuth) {
        if (to.path == '/') {
            next('/gists/public')
        }
        else {
            document.getElementById('dialog').__vue__.alert('ログインしませんか？', 'ログインするとGistの作成や1時間あたりのリクエスト回数制限が60回から5000回になります！')
            next(false)
        }
    }
    else next()
})

window.addEventListener('load', (e) => {
    window.vm = new Vue({
        router: router
    }).$mount('#content')
})
