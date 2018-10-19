let imageObserver
if ('IntersectionObserver' in window) {
    imageObserver = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting && 'url' in entry.target.dataset && entry.target.dataset.url.length > 0) {
                entry.target.src = entry.target.dataset.url
                delete entry.target.dataset.url
                imageObserver.unobserve(entry.target)
            }
        })
    })
}

export default {
    props: ['url', 'id', 'detail'],
    watch: {
        'id': function (newVal, oldVal) {
            this.reset()
            this.getGist()
                .then((json) => {
                    this.setGist(json)
                    if (this.url != null && this.url.startsWith('blob')) URL.revokeObjectURL(this.url)
                })
        }
    },
    template: `<div class='gist-item' v-bind:class='{"detail":detail != null}'>
                    <div class='root' v-if='(url != null || id != null) && gist != null'>
                        <a v-bind:href='gist.owner.html_url' class='icon'>
                            <img v-bind:data-url='gist.owner.avatar_url' v-if='lazyLoad' />
                            <img v-bind:src='gist.owner.avatar_url' v-if='!lazyLoad' />
                        </a>
                        <div v-bind:class='{"secret":!gist.public}'>
                            <a v-bind:href='gist.owner.html_url' class='name'>{{ gist.owner.login }}</a> / <a v-bind:href='gist.html_url' class='files'>{{ Object.keys(gist.files).join(', ') }}</a>
                        </div>
                        <span class='date'>{{ gist.created_at | dateFormat }}</span>
                        <span class='desc'>{{ gist.description || "No description." }}</span>
                        <span class='comments' v-if='detail != null'>コメント：<a v-bind:href='gist.html_url + "#comments"'>{{ gist.comments }}件</a></span>
                        <div v-if='!detail' class='open'>
                            <router-link v-bind:to='"/gists/" + gist.id'>詳しく見る</router-link>
                        </div>
                        <div v-if='detail && isAuth' class='buttons'>
                            <button class='star' v-bind:class='{"starred":starred}' v-on:click='star()'>Star</button>
                            <button v-if='editable && !confirmFlag' class='delete' v-on:click='confirm()'>Delete</button>
                            <button v-if='editable && confirmFlag' class='delete confirm' v-on:click='del()'>よろしいですか？</button>
                            <button v-if='!editable && !forked' class='forks' v-on:click='fork()'>Fork : {{ gist.forks.length }}</button>
                            <button v-if='!editable && forked' class='forks'>しばらくお待ちください...</button>
                        </div>
                        <div class='gist' v-if='detail != null'></div>
                    </div>
                </div>`,
    data: function () {
        return {
            'gist': null,
            'lazyLoad': false,
            'editable': false,
            'isAuth': false,
            'starred': false,
            'forked': false,
            'confirmFlag': false,
        }
    },
    created: function () {
        this.lazyLoad = imageObserver != null
        this.isAuth = 'accessToken' in localStorage
        this.getGist()
            .then((json) => {
                this.setGist(json)
                if (this.url != null && this.url.startsWith('blob')) URL.revokeObjectURL(this.url)
            })
    },
    methods: {
        getGist: async function () {
            const url = this.url || `https://api.github.com/gists/${this.id}`
            if (this.url != null || this.id != null) {
                const headers = new Headers()
                if (this.isAuth) {
                    headers.append('Authorization', ` token ${localStorage.getItem('accessToken')}`)
                }
                const response = await fetch(url, {
                    'headers': headers
                })
                if(response.ok) return await response.json()
                else this.$router.go(-1)
            }
        },
        setGist: function (json) {
            this.gist = json
            this.editable = this.gist.owner.login == localStorage.getItem('username')

            if (this.lazyLoad) {
                this.$nextTick().then(() => {
                    imageObserver.observe(this.$el.getElementsByTagName('img')[0])
                })
            }

            if (this.detail != null) {
                this.$nextTick().then(() => {
                    const iframe = document.createElement('iframe')
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

                    this.$el.getElementsByClassName('gist')[0].appendChild(iframe)
                    const iframeDoc = iframe.contentWindow.document
                    const html = `<body style='margin:0;overflow-y:hidden;'>
                                    <script src="https://gist.github.com/${this.gist.owner.login}/${this.gist.id}.js"></script>
                                </body>`
                    iframeDoc.open()
                    iframeDoc.write(html)
                    iframeDoc.close()
                })

                if (this.isAuth) {
                    this.isStarred(this.gist.id)
                        .then((starred) => {
                            this.starred = starred
                        })
                }
            }
        },
        isStarred: async function () {
            const response = await fetch(`https://api.github.com/gists/${this.gist.id}/star`, {
                'headers': new Headers({ 'Authorization': ` token ${localStorage.getItem('accessToken')}` })
            })
            if (response.status == 204) {
                return true
            }
            else if (response.status == 404) {
                return false
            }
            else return null
        },
        star: function () {
            let method
            if (this.starred) {
                method = 'DELETE'
            }
            else {
                method = 'PUT'
            }
            fetch(`https://api.github.com/gists/${this.gist.id}/star`, {
                'method': method,
                'headers': new Headers({ 'Authorization': ` token ${localStorage.getItem('accessToken')}` })
            })
                .then((response) => {
                    if (response.status == 204) this.starred = !this.starred
                })
        },
        fork: function () {
            this.forked = true
            fetch(`https://api.github.com/gists/${this.gist.id}/forks`, {
                'method': 'POST',
                'headers': new Headers({ 'Authorization': ` token ${localStorage.getItem('accessToken')}` })
            })
                .then((response) => response.json())
                .then((json) => {
                    this.$router.push(`/gists/${json.id}`)
                })
        },
        del: function () {
            fetch(`https://api.github.com/gists/${this.gist.id}`, {
                'method': 'DELETE',
                'headers': new Headers({ 'Authorization': ` token ${localStorage.getItem('accessToken')}` })
            })
                .then((response) => {
                    if (response.status == 204) this.$router.push('/')
                })
        },
        confirm: function () {
            this.confirmFlag = true
            setTimeout(() => {
                this.confirmFlag = false
            }, 1000 * 5)
        },
        reset: function () {
            this.gist = null
            this.editable = this.starred = this.forked = false
        }
    }
}