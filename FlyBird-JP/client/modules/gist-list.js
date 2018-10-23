import MyDialog from '/modules/my-dialog.js'
import GistItem from '/modules/gist-item.js'

const urls = {
    'mine': 'https://api.github.com/gists',
    'user': 'https://api.github.com/users',
    'public': 'https://api.github.com/gists/public',
    'starred': 'https://api.github.com/gists/starred',
}

export default {
    props: {
        'user': {
            type: String,
            default: 'mine'
        },
        'name': {
            type: String
        },
        'limit': {
            type: Number,
            default: 20
        }
    },
    watch: {
        'name': function (newVal, oldVal) {
            this.gists = []
            this.isLast = false
            this.print(this.getUrl())
        }
    },
    template: `<div class='gist-list'>
                    <div class='root' v-if='log.length == 0'>
                        <gist-item 
                            v-for='gist in gists'
                            v-bind:url='gist'
                        ></gist-item>
                        <button class='load' v-on:click='print(nextUrl)' v-if='gists != null && gists.length > 0 && !infiniteScroll && nextUrl != null'>さらに読み込む</button>
                    </div>
                    <div class='message center' v-if='log.length > 0'>{{ log }}</div>
                    <my-dialog></my-dialog>
                </div>`,
    components: {
        'gist-item': GistItem,
        'my-dialog': MyDialog
    },
    data: function () {
        return {
            'gists': null,
            'infiniteScroll': false,
            'log': '',
            'nextUrl': null
        }
    },
    created: function () {
        this.infiniteScroll = ('IntersectionObserver' in window && 'MutationObserver' in window)
        if ('IntersectionObserver' in window) {
            this.intersectionObserver = new IntersectionObserver((entries) => {
                entries.forEach((entry) => {
                    if ((entry.isIntersecting && this.$el.parentElement.scrollTop > 0)) {
                        this.intersectionObserver.unobserve(entry.target)
                        if (!this.isLast) this.print(this.nextUrl)
                    }
                })
            })
        }
    },
    mounted: function () {
        if (this.intersectionObserver != null) {
            (new MutationObserver((mutations) => {
                for (let i = mutations.length - 1; i >= 0; i--) {
                    if (mutations[i].addedNodes.length > 0) {
                        const lastChild = mutations[i].addedNodes[mutations[i].addedNodes.length - 1]
                        if ('classList' in lastChild && lastChild.classList.contains('gist-item')) {
                            this.intersectionObserver.observe(lastChild)
                            break
                        }
                    }
                }
            })).observe(this.$el.getElementsByClassName('root')[0], { 'childList': true })
        }
        this.print(this.getUrl())
    },
    methods: {
        print: function (url) {
            if (url != null) {
                this.load(url)
                    .then(this.addGist)
                    .catch((err) => {
                        this.$el.getElementsByClassName('my-dialog')[0].__vue__.alert('エラーが発生しました。', err.toString())
                        this.log = err.toString()
                    })
            }
        },
        getUrl: function () {
            this.isAuth = 'accessToken' in localStorage
            if (!(this.user in urls)) this.url = urls['public']
            else if (this.name != null) {
                this.url = `${urls['user']}/${this.name}/gists`
            }
            else if (this.isAuth) {
                this.url = urls[this.user]
            }
            else this.url = urls['public']

            return `${this.url}?per_page=${this.limit}`
        },
        load: async function (url) {
            const headers = new Headers()
            if (this.isAuth) {
                headers.append('Authorization', ` token ${localStorage.getItem('accessToken')}`)
            }
            const response = await fetch(url, { 'headers': headers, 'cache': 'no-cache' })
            if (response.ok) {
                this.nextUrl = null
                if (response.headers.has('Link')) {
                    const links = response.headers.get('Link')
                    const linkPattern = /<(http(?:s)?:\/\/(?:[\w-]+\.)+[\w-]+(?:\/[\w-.\/?%&=]*)?)>; rel="(.+?)"/g
                    let match
                    while ((match = linkPattern.exec(links)) != null) {
                        if (match[2] == 'next') {
                            this.nextUrl = match[1]
                            break;
                        }
                    }
                }

                return await response.json()
            }
            else throw new Error(`${response.status} ${response.statusText}`)
        },
        addGist: function (json) {
            const gists = []
            for (let item of json) {
                const blob = new Blob([JSON.stringify(item)], { type: 'application/json' })
                gists.push(URL.createObjectURL(blob))
            }
            if (this.gists == null) this.gists = []
            this.gists = this.gists.concat(gists)
            if (this.gists.length == 0) this.log = '表示できるGistは今のところありません。'
        }
    }
}