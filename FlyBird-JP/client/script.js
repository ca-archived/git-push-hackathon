'use strict'

const routes = [
    {
        'path': '/',
        'component': {
            'template': '<div>top page</div>'
        }
    },
    {
        'path': '/callback_auth',
        'component': {
            'template': '<div>foo</div>',
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
                }
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