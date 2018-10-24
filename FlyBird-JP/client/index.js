'use strict';

import LoginGithub from '/modules/login-github.js'
Vue.component('login-github', LoginGithub)

import MyDialog from '/modules/my-dialog.js'
Vue.component('my-dialog', MyDialog)

import GistEditor from '/modules/gist-editor.js'
Vue.component('gist-editor', GistEditor)

import GistItem from '/modules/gist-item.js'
Vue.component('gist-item', GistItem)

Vue.filter('dateFormat', (date) => {
    let differ = (new Date() - new Date(date)) / 1000
    if(differ < 0) differ = 0
    if (differ > 7 * 24 * 60 * 60) return new Date(date).toLocaleDateString()
    if (differ > 24 * 60 * 60) return `${Math.floor(differ / (24 * 60 * 60))}日`
    else if (differ > 60 * 60) return `${Math.floor(differ / (60 * 60))}時間`
    else if (differ > 60) return `${Math.floor(differ / 60)}分`
    else return `${Math.floor(differ)}秒`
})