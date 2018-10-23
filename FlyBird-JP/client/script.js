'use strict'
import routes from '/routes.js'

const router = new VueRouter({
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

const needAuthPaths = ['/', '/gists/new', '/gists/starred']
router.beforeEach((to, from, next) => {
    if (!('accessToken' in localStorage) && needAuthPaths.includes(to.path)) {
        if (to.path == '/') {
            next('/gists/public')
        }
        else {
            document.getElementById('dialog').__vue__.alert('ログインしませんか？', 'ログインするとGistの作成や1時間あたりのリクエスト回数制限が60回から5000回になります！')
            next(false)
        }
    }
    else {
        switch (to.path) {
            case '/':
                if (from.path == '/gists/new') {
                    document.getElementById('dialog').__vue__.confirm('このページを離れてもよろしいですか？', '作成途中の場合、その内容は失われてしまいます。', (bool) => {
                        if (bool) next()
                        else next(false)
                    })
                }
                else next()
                break;
            case '/users':
                document.getElementById('dialog').__vue__.prompt('ユーザー名を入力してください。', '指定したユーザーのGistを表示します。', (input) => {
                    if (input != null) next(`/users/${input.trim()}/gists`)
                    else next(false)
                })
                break;
            case '/logout':
                localStorage.clear(`accessToken`)
                localStorage.clear(`username`)
                location.href = '/'
                break;
            default: next()
                break;
        }
    }

    if (to.path.startsWith('/callback_auth')) document.getElementById('buttons').style.visibility = 'hidden'
    if (from.path.startsWith('/callback_auth')) document.getElementById('buttons').style.visibility = 'visible'
})

window.addEventListener('load', (e) => {
    window.vm = new Vue({
        'router': router
    }).$mount('#content')
})
