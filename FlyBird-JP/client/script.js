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
                            </div>
                        </div>
                        <div class='content with_tab'>
                            <router-link class='add' to='/gists/new'>作成する</router-link>
                            <gist-list user='user'></gist-list>
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
                        <div class='content with_padding'>
                            <h2>Gistの新規作成</h2>
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
        'path': '/gists/:id',
        'component': {
            'template': `
                <div id='root'>
                    <main>
                        {{ $route.params.id }}
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
                        <div class="spinner center"></div>
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
                        .then((response) => response.json())
                        .then((json) => {
                            localStorage.setItem(`accessToken`, json['accessToken'])
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

router.beforeEach((to, from, next) => {
    if (to.path.startsWith('/callback_auth')) document.getElementById('loginGithub').style.visibility = 'hidden'
    if (from.path.startsWith('/callback_auth')) document.getElementById('loginGithub').style.visibility = 'visible'

    if (['/', '/gists/starred'].includes(to.path) && !('accessToken' in localStorage)) {
        if (from.path == '/gists/public') {
            document.getElementById('dialog').__vue__.alert('ログインしませんか？', 'ログインするとGistの作成や1時間あたりのリクエスト回数制限が60回から5000回になります！')
        }
        next('/gists/public')
    }
    else next()
})

window.addEventListener('load', (e) => {
    window.vm = new Vue({
        'router': router
    }).$mount('#content')
})
