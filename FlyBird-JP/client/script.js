'use strict'

Vue.config.ignoredElements = ['login-button', 'gist-list', 'gist-item', 'git-event'];

const routes = [
    {
        'path': '/',
        'component': {
            'template': `
                <div id='root'>
                    <header>
                        <h1>
                            <a href='/'>Gist Client</a>
                        </h1>
                        <div id='buttons'>
                            <login-button service='github'></login-button>
                        </div>
                    </header>
                    <main>
                        <gist-list></gist-list>
                    </main>
                </div>
            `
        }
    },
    {
        'path': '/callback_auth',
        'component': {
            'template': `
                <div id='root'>
                    <header>
                        <h1>
                            <router-link to='/'>Gist Client</router-link>
                        </h1>
                    </header>
                    <main class='relative'>
                        <div class="spinner center"></div>
                    </main>
                </div>
            `,
            'created': function () {
                const url = new URL(location.href)
                if (url.searchParams.has('code') && url.searchParams.has('service') && url.searchParams.has('state')) {
                    fetch('/token', {
                        'method': 'POST',
                        'body': JSON.stringify({
                            'code': url.searchParams.get('code'),
                            'service' : url.searchParams.get('service'),
                            'state' : url.searchParams.get('state')
                        }),
                        'headers': new Headers({ 'Content-type': 'application/json' })
                    })
                        .then((response) => response.json())
                        .then((json) => {
                            localStorage.setItem(`${json['service']}AccessToken`, json['accessToken'])
                            location.href = '/'
                        })
                } else ;//location.href = '/'
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

window.addEventListener('load', (e) => {
    new Vue({
        'router': router
    }).$mount('#content')
})