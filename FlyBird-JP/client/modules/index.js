"use strict";

import LoginGithub from '/modules/login-github.js'
Vue.component('login-github', LoginGithub)
//window.customElements.define('login-github', LoginGithub)

import GithubEvent from '/modules/github-event.js'
Vue.component('git-event', GithubEvent)
//window.customElements.define('git-event', GithubEvent)

import GistList from '/modules/gist-list.js'
Vue.component('gist-list', GistList)
//window.customElements.define('gist-list', GistList)

import GistItem from '/modules/gist-item.js'
Vue.component('gist-item', GistItem)
//window.customElements.define('gist-item', GistItem)

import MyDialog from '/modules/my-dialog.js'
Vue.component("my-dialog", MyDialog);

import GistEditor from '/modules/gist-editor.js'
Vue.component("gist-editor", GistEditor);

Vue.filter('dateFormat', (date) => {
    let differ = (new Date() - new Date(date)) / 1000
    if(differ < 0) differ = 0
    if (differ > 7 * 24 * 60 * 60) return new Date(date).toLocaleDateString()
    if (differ > 24 * 60 * 60) return `${Math.floor(differ / (24 * 60 * 60))}日`
    else if (differ > 60 * 60) return `${Math.floor(differ / (60 * 60))}時間`
    else if (differ > 60) return `${Math.floor(differ / 60)}分`
    else return `${Math.floor(differ)}秒`
})