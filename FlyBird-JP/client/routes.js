export default [
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
                </div>`,
            'mounted': function () {
                document.getElementsByClassName('login-github')[0].__vue__.isActive = false
            }
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
            }
        }
    },
    {
        'path': '/users',
        'component': {
            'template': `
                <div id='root'>
                    <main>
                    <div class='tab_area'>
                        <div class='tabs'>
                            <h2 class='tab'><router-link to='/'>Yours</router-link></h2>
                            <h2 class='tab'><router-link to='/gists/starred'>Starred</router-link></h2>
                            <h2 class='tab'><router-link to='/gists/public'>Public</router-link></h2>
                            <h2 class='tab active'><router-link to='/users'>ユーザー</router-link></h2>
                        </div>
                    </div>
                    <div class='content with_tab'>
                        <user-list type='following'></user-list>
                    </div>
                </div>`
        }
    },
    {
        'path': '*',
        'beforeEnter': (to, from, next) => {
            next('/')
        }
    }
]