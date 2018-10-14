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