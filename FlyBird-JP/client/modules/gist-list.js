import MyDialog from '/modules/my-dialog.js'
import GistItem from '/modules/gist-item.js'

export default {
    props: {
        'user': {
            type: String,
            default: 'me'
        },
        'starred': [String, Number],
        'name': String,
        'limit': {
            type: Number,
            default: 20
        },
        'token': String
    },
    watch: {
        'user': function (newVal, oldVal) {
            this.reset()
        },
        'name': function (newVal, oldVal) {
            this.reset()
        },
        'starred': function (newVal, oldVal) {
            this.reset()
        }
    },
    template: `<div class='gist-list'>
                    <div class='root' v-if='log.length == 0'>
                        <gist-item 
                            v-for='gist in gists'
                            v-bind:url='gist.url'
                            v-bind:key='gist.id'
                            v-bind:token='token'
                        >
                            <router-link v-bind:to='"/gists/" + gist.id' slot='link'>詳しく見る</router-link>
                        </gist-item>
                        <button class='load' v-on:click='print(nextUrl)' v-if='gists != null && gists.length > 0 && !infiniteScroll && nextUrl != null'>さらに読み込む</button>
                    </div>
                    <div class='message center' v-if='log.length > 0'>{{ log }}</div>
                    <my-dialog ref='dialog'></my-dialog>
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
            this.load(url)
                .then(this.addGist)
                .catch((err) => {
                    this.$refs.dialog.alert('エラーが発生しました。', err.toString())
                    this.log = err.toString()
                })
        },
        getUrl: function () {
            let url

            if (this.user == 'user' && this.name != null) {
                if (this.starred == null) url = `https://api.github.com/users/${this.name}/gists`
                else url = `https://api.github.com/users/${this.name}/gists/starred`
            }
            else if (this.user == 'me' && this.token != null) {
                if (this.starred == null) url = `https://api.github.com/gists`
                else url = `https://api.github.com/gists/starred`
            }
            else url = 'https://api.github.com/gists/public'

            return `${url}?per_page=${this.limit}`
        },
        load: async function (url) {
            const headers = new Headers()
            if (this.token != null) {
                headers.append('Authorization', ` token ${this.token}`)
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
                gists.push({
                    'id': item.id,
                    'url': URL.createObjectURL(blob)
                })
            }
            if (this.gists == null) this.gists = []
            this.gists = this.gists.concat(gists)

            if (this.gists.length == 0) this.log = '表示できるGistは今のところありません。'
        },
        reset: function () {
            this.gists = null
            this.isLast = false
            this.log = ''
            this.print(this.getUrl())
        }
    }
}