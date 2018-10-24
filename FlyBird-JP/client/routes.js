import GistList from '/modules/gist-list.js'

export default [
    {
        path: '/',
        component: {
            data: function () {
                return {
                    'yours': false,
                    'starred': false,
                    'public': false,
                    'user': false
                }
            },
            template:
                `<main>
                    <div class='tab_area'>
                        <div class='tabs'>
                            <h2 class='tab' v-bind:class='{"active":$route.name == "yours"}'>
                            <router-link to='/'>Yours</router-link>
                            </h2>
                            <h2 class='tab' v-bind:class='{"active":$route.name == "starred"}'>
                                <router-link to='/gists/starred'>Starred</router-link>
                            </h2>
                            <h2 class='tab' v-bind:class='{"active":$route.name == "public"}'>                                    <router-link to='/gists/public'>Public</router-link>
                                </h2>
                            <h2 class='tab' v-bind:class='{"active":["usres", "users gists"].includes($route.name)}'>
                                <router-link to='/users' v-if='!["usres", "users gists"].includes($route.name)'>入力する</router-link>
                                <router-link v-bind:to='$route.path' v-if='$route.name == "users gists"'>{{$route.params.username}}</router-link>
                            </h2>
                        </div>
                    </div>
                    <div class='content with_tab'>
                        <router-view></router-view>
                    </div>
                </main>`
        },
        children: [
            {
                path: '',
                name: 'yours',
                component: GistList,
                meta: { 'requiresAuth': true }
            },
            {
                path: 'gists/starred',
                name: 'starred',
                component: GistList,
                props: {
                    'starred': 'starred'
                },
                meta: { 'requiresAuth': true }
            },
            {
                path: 'gists/public',
                name: 'public',
                component: GistList,
                props: {
                    'user': 'public'
                },
                meta: { 'requiresAuth': false }
            },
            {
                path: 'users',
                component: {
                    template: `<router-view></router-view>`
                },
                children: [
                    {
                        path: '',
                        name: 'users',
                        component: {
                            beforeRouteEnter(to, from, next) {
                                document.getElementById('dialog').__vue__.prompt('ユーザー名を入力してください。', '指定したユーザーのGistを表示します。', (input) => {
                                    if (input != null) next(`/users/${input.trim()}/gists`)
                                    else next(false)
                                })
                            }
                        },
                        meta: { 'requiresAuth': false }
                    },
                    {
                        path: ':username/gists',
                        name: "users gists",
                        component: GistList,
                        props: (route) => {
                            return {
                                'user': 'user',
                                'name': route.params.username
                            }
                        },
                        beforeEnter: function (to, from, next) {
                            const loginButton = document.getElementsByClassName('login-github')[0]
                            if (loginButton != null) loginButton.__vue__.isActive = false
                            next()
                        },
                        meta: { 'requiresAuth': false }
                    }
                ]
            }
        ]
    },
    {
        path: '/gists/new',
        component: {
            data: function () {
                return {
                    'token': localStorage.getItem('accessToken')
                }
            },
            template:
                `<main>
                    <div class='tab_area'>
                        <div class='content'>
                            <h2>Gistの新規作成</h2>
                        </div>
                    </div>
                    <div class='content with_tab with_padding'>
                        <gist-editor
                            v-bind:token='token'
                        ></gist-editor>
                    </div>
                </main>`,
            beforeRouteLeave: function (to, from, next) {
                if (to.path == '/') {
                    document.getElementById('dialog').__vue__.confirm('このページを離れてもよろしいですか？', '作成途中の場合、その内容は失われてしまいます。', (bool) => {
                        if (bool) next()
                        else next(false)
                    })
                }
                else next()
            }
        },
        meta: { 'requiresAuth': true }
    },
    {
        path: '/gists/:id',
        component: {
            data: function () {
                return {
                    'me': localStorage.getItem('username')
                }
            },
            template:
                `<main>
                    <div class='content'>
                        <gist-item
                            v-bind:id='this.$route.params.id'
                            detail='detail'
                            v-bind:token='token'
                            v-bind:me='me'
                        ></gist-item>
                    </div>
                </main>`
        },
        meta: { 'requiresAuth': false }
    },
    {
        path: '/callback_auth/:service',
        component: {
            template:
                `<main class='relative'>
                    <div class='spinner center'></div>
                </main>`,
            created: function () {
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
                            else throw new Error(response.status)
                        })
                        .then(() => {
                            location.href = '/'
                        })
                        .catch((err) => {
                            document.getElementById('dialog').__vue__.alert('エラーが発生しました。', '操作をやり直してください。', () => {
                                location.href = '/'
                            })
                        })
                } else location.href = '/'
            },
            beforeRouteEnter: function (to, from, next) {
                document.getElementById('buttons').style.visibility = 'hidden'
                next()
            },
            beforeRouteLeave: function (to, from, next) {
                document.getElementById('buttons').style.visibility = 'visible'
                next()
            },
        },
        meta: { 'requiresAuth': false }
    },
    {
        path: '/logout',
        beforeEnter: (to, from, next) => {
            localStorage.clear(`accessToken`)
            localStorage.clear(`username`)
            location.href = '/'
        },
        meta: { 'requiresAuth': true }
    },
    {
        path: '*',
        beforeEnter: (to, from, next) => {
            next('/')
        },
        meta: { 'requiresAuth': false }
    }
]