import MyDialog from '/modules/my-dialog.js'
import GithubEvent from '/modules/github-event.js'

export default {
    props: {
        'limit': {
            type: Number,
            default: 20
        },
        'username': {
            type: String
        },
        'url': {
            type: String
        }
    },
    template: `<div class='event-list' v-if='username != null || url != null'>
                    <div class='root' v-if='log.length == 0'>
                        <github-event 
                            v-for='event in events'
                            v-bind:url='event'
                        ></github-event>
                        <button class='load' v-on:click='print(nextUrl)' v-if='!infiniteScroll && nextUrl != null'>さらに読み込む</button>
                    </div>
                    <div class='message center' v-if='log.length > 0'>{{ log }}</div>
                    <my-dialog></my-dialog>
                </div>`,
    components: {
        'github-event': GithubEvent,
        'my-dialog': MyDialog
    },
    data: function () {
        return {
            'events': null,
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
                    if ((entry.isIntersecting && this.$el.scrollTop > 0)) {
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
                        if ('classList' in lastChild && lastChild.classList.contains('github-event')) {
                            this.intersectionObserver.observe(lastChild)
                            break
                        }
                    }
                }
            })).observe(this.$el.getElementsByClassName('root')[0], { 'childList': true });
        }

        this.print(this.url || `https://api.github.com/users/${this.username}/received_events?per_page=${this.limit}`)
    },
    methods: {
        print: function (url) {
            this.load(url)
                .then(this.addEvent)
                .catch((err) => {
                    this.$el.getElementsByClassName('my-dialog')[0].__vue__.alert('エラーが発生しました。', err.toString())
                    this.log = err.toString()
                })
        },
        load: async function (url) {
            const response = await fetch(url)
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
        addEvent: function (json) {
            const events = []
            for (let item of json) {
                const blob = new Blob([JSON.stringify(item)], { type: 'application/json' })
                events.push(URL.createObjectURL(blob))
            }
            if (this.events == null) this.events = []
            this.events = this.events.concat(events)
            if (this.events.length == 0) this.log = '表示できるイベントは今のところありません。'
        }
    }
}