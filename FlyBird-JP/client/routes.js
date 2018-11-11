function openGist(event) {
    if (event.srcElement.classList.contains('gist-item')) {
        const username = event.srcElement.__vue__.gist.owner.login
        const id = event.srcElement.__vue__.gist.id
        router.push(`/users/${username}/gists/${id}`)
    }
}

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
                            <h2 class='tab' v-bind:class='{"active":$route.name == "public"}'>                               
                                <router-link to='/gists/public'>Public</router-link>
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
                component: {
                    template: `<gist-list ref='list'></gist-list>`,
                    mounted: function () {
                        this.$refs.list.$el.addEventListener('click', openGist)
                    }
                },
                meta: { 'requiresAuth': true }
            },
            {
                path: 'gists/starred',
                name: 'starred',
                component: {
                    template: `<gist-list starred='starred' ref='list'></gist-list>`,
                    mounted: function () {
                        this.$refs.list.$el.addEventListener('click', openGist)
                    }
                },
                meta: { 'requiresAuth': true }
            },
            {
                path: 'gists/public',
                name: 'public',
                component: {
                    template: `<gist-list user='public' ref='list'></gist-list>`,
                    mounted: function () {
                        this.$refs.list.$el.addEventListener('click', openGist)
                    }
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
                        name: 'users gists',
                        component: {
                            template: `<gist-list user='user' v-bind:name='$route.params.username' ref='list'></gist-list>`,
                            mounted: function () {
                                this.$refs.list.$el.addEventListener('click', openGist)
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
                        <gist-editor></gist-editor>
                    </div>
                </main>`,
            beforeRouteLeave: function (to, from, next) {
                console.log(to)
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
        path: '/users/:username/gists/:id',
        component: {
            template:
                `<main>
                    <div class='content'>
                        <gist-item
                            v-bind:id='this.$route.params.id'
                            v-bind:me='me'
                        ></gist-item>
                        <div class="iframe"></div>
                    </div>
                </main>`,
            data: function () {
                return {
                    'me': localStorage.getItem('username')
                }
            },
            mounted: function () {
                const iframe = document.createElement('iframe')
                this.$el.getElementsByClassName('iframe')[0].appendChild(iframe)
                const iframeDoc = iframe.contentWindow.document
                const html = `<body style='margin:0;overflow-y:hidden;'>
                        <script src="https://gist.github.com/${this.$route.params.username}/${this.$route.params.id}.js"></script>
                    </body>`
                iframeDoc.open()
                iframeDoc.write(html)
                iframe.addEventListener('load', (event) => {
                    const doc = iframe.contentWindow.document
                    if (doc.getElementsByClassName('gist').length > 0) {
                        iframe.style.height = `${doc.documentElement.scrollHeight}px`
                    }
                    iframe.contentWindow.addEventListener('click', (event) => {
                        if (event.srcElement.tagName.toLowerCase() == 'a') {
                            location.href = event.srcElement.href
                            event.preventDefault()
                        }
                    })
                })
                iframeDoc.close()
            }
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