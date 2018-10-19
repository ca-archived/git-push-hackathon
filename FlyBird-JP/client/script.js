'use strict'

const routes = [
    {
        'path': '/',
        'component': {
            'template': `
                <div id='root'>
                    <main>
                        <div class='tab_area'>
                            <div class='tabs'>
                                <h2 class='tab active'><router-link to='/'>Yours</router-link></h2>
                                <h2 class='tab'><router-link to='/gists/starred'>Starred</router-link></h2>
                                <h2 class='tab'><router-link to='/gists/public'>Public</router-link></h2>
                                <h2 class='tab'><router-link to='/users'>入力する</router-link></h2>
                            </div>
                        </div>
                        <div class='content with_tab'>
                            <gist-list></gist-list>
                        </div>
                    </main>
                </div>`
        }
    },
    {
        'path': '/gists/starred',
        'component': {
            'template': `
                <div id='root'>
                    <main>
                        <div class='tab_area'>
                            <div class='tabs'>
                                <h2 class='tab'><router-link to='/'>Yours</router-link></h2>
                                <h2 class='tab active'><router-link to='/gists/starred'>Starred</router-link></h2>
                                <h2 class='tab'><router-link to='/gists/public'>Public</router-link></h2>
                                <h2 class='tab'><router-link to='/users'>入力する</router-link></h2>
                            </div>
                        </div>
                        <div class='content with_tab'>
                            <gist-list user='starred'></gist-list>
                        </div>
                    </main>
                </div>`
        }
    },
    {
        'path': '/gists/new',
        'component': {
            'template': `
                <div id='root'>
                    <main>
                        <div class='tab_area'>
                            <div class='content'>
                                <h2>Gistの新規作成</h2>
                            </div>
                        </div>
                        <div class='content with_tab with_padding'>
                            <gist-editor></gist-editor>
                        </div>
                    </main>
                </div>`
        }
    },
    {
        'path': '/gists/public',
        'component': {
            'template': `
                <div id='root'>
                    <main>
                        <div class='tab_area'>
                            <div class='tabs'>
                                <h2 class='tab'><router-link to='/'>Yours</router-link></h2>
                                <h2 class='tab'><router-link to='/gists/starred'>Starred</router-link></h2>
                                <h2 class='tab active'><router-link to='/gists/public'>Public</router-link></h2>
                                <h2 class='tab'><router-link to='/users'>入力する</router-link></h2>
                            </div>
                        </div>
                        <div class='content with_tab'>
                            <gist-list user='public'></gist-list>
                        </div>
                    </main>
                </div>`
        }
    },
    {
        'path': '/users/:username/gists',
        'component': {
            'template': `
                <div id='root'>
                    <main>
                    <div class='tab_area'>
                        <div class='tabs'>
                            <h2 class='tab'><router-link to='/'>Yours</router-link></h2>
                            <h2 class='tab'><router-link to='/gists/starred'>Starred</router-link></h2>
                            <h2 class='tab'><router-link to='/gists/public'>Public</router-link></h2>
                            <h2 class='tab active'><router-link to='/users'>{{ $route.params.username }}</router-link></h2>
                        </div>
                    </div>
                    <div class='content with_tab'>
                    <gist-list user='user' v-bind:name='$route.params.username'></gist-list>
                </div>
            </main>
                </div>`
        }
    },
    {
        'path': '/gists/:id',
        'component': {
            'template': `
                <div id='root'>
                    <main>
                        <div class='content'>
                        <gist-item v-bind:id='this.$route.params.id' detail='detail'></gist-item>
                    </div>
                    </main>
                </div>`
        }
    },
    {
        'path': '/callback_auth/:service',
        'component': {
            'template': `
                <div id='root'>
                    <main class='relative'>
                        <div class='spinner center'></div>
                    </main>
                </div>`,
            'created': function () {
                const url = new URL(location.href)
                if (url.searchParams.has('code') && url.searchParams.has('state')) {
                    fetch(`/token/${this.$route.params['service']}`, {
                        'method': 'POST',
                        'body': JSON.stringify({
                            'code': url.searchParams.get('code'),
                            'state': url.searchParams.get('state')
                        }),
                        'headers': new Headers({ 'Content-type': 'application/json' })
                    })
                        .then(async (response) => {
                            if (response.ok) {
                                const json = await response.json()
                                localStorage.setItem(`accessToken`, json['accessToken'])
                            }
                        }).then(() => {
                            location.href = '/'
                        })
                } else location.href = '/'
            }
        }
    }
]
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
    if (to.path.startsWith('/callback_auth')) document.getElementById('buttons').style.visibility = 'hidden'
    if (from.path.startsWith('/callback_auth')) document.getElementById('buttons').style.visibility = 'visible'

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
                    document.getElementById('dialog').__vue__.confirm('このページを離れてもよろしいですか？', '作成途中場合、その内容は失われてしまいます。', (bool) => {
                        if (bool) next()
                        else next(false)
                    })
                }
                else next()
                break;
            case '/users':
                document.getElementById('dialog').__vue__.prompt('ユーザー名を入力してください。', '指定したユーザーのGistsを表示します。', (input) => {
                    if (input != false) next(`/users/${input.trim()}/gists`)
                    else next(false)
                })
                break;
            default: next()
                break;
        }
    }
})

window.addEventListener('load', (e) => {
    window.vm = new Vue({
        'router': router
    }).$mount('#content')
})
