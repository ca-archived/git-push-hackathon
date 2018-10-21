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
            this.setUrl()
            this.load()
        }
    },
    template: `<div class='gist-list'>
                    <gist-item 
                        v-bind:url='gist.url'
                        v-for='gist in gists'
                    ></gist-item>
                    <button class='load' v-on:click='load()' v-if='gists != null && gists.length > 0 && !infiniteScroll && !isLast'>さらに読み込む</button>
                    <div class='messeage center' v-if='gists != null && gists.length == 0'>表示できる gist がまだありません。</div>
                </div>`,
    data: function () {
        return {
            'gists': null,
            'isLast': false,
            'infiniteScroll': false
        }
    },
    created: function () {
        this.infiniteScroll = ('IntersectionObserver' in window && 'MutationObserver' in window)
        if ('IntersectionObserver' in window) {
            this.intersectionObserver = new IntersectionObserver((entries) => {
                entries.forEach((entry) => {
                    if ((entry.isIntersecting && this.$el.parentElement.scrollTop > 0)) {
                        this.intersectionObserver.unobserve(entry.target);
                        this.load()
                    }
                })
            })
        }
        this.setUrl()
    },
    mounted: function () {
        if (this.intersectionObserver != null) {
            (new MutationObserver((mutations) => {
                for (let i = mutations.length - 1; i >= 0; i--) {
                    if (mutations[i].addedNodes.length > 0) {
                        const lastChild = mutations[i].addedNodes[mutations[i].addedNodes.length - 1]
                        if ('classList' in lastChild && lastChild.classList.contains('gist-item')) {
                            this.intersectionObserver.observe(lastChild);
                            break
                        }
                    }
                }
            })).observe(this.$el, { 'childList': true });
        }
        this.load()
    },
    methods: {
        setUrl: function () {
            this.isAuth = 'accessToken' in localStorage
            if (!(this.user in urls)) this.url = urls['public']
            else if (this.name != null) {
                this.url = `${urls['user']}/${this.name}/gists`
            }
            else if (this.isAuth) {
                this.url = urls[this.user]
            }
            else this.url = urls['public']

            this.url += `?per_page=${this.limit}`
        },
        load: function () {
            if (!this.isLast) {
                const headers = new Headers()
                if (this.isAuth) {
                    headers.append('Authorization', ` token ${localStorage.getItem('accessToken')}`)
                }
                fetch(this.url, {
                    'headers': headers,
                    'cache': 'no-cache'
                })
                    .then(async (response) => {
                        if (response.ok) {
                            if (response.headers.has('Link')) {
                                const links = response.headers.get('Link')
                                const linkPattern = /<(http(?:s)?:\/\/(?:[\w-]+\.)+[\w-]+(?:\/[\w-.\/?%&=]*)?)>; rel="(.+?)"/g
                                let match
                                while ((match = linkPattern.exec(links)) != null) {
                                    if (match[2] == 'next') {
                                        this.url = match[1]
                                        break;
                                    }
                                }
                            }
                            else this.isLast = true

                            this.addGist(await response.json())
                        }
                        else throw new Error(response.status)
                    }).catch((err) => {
                        console.log(err)
                    })
            }
        },
        addGist: function (json) {
            const gists = []
            for (let item of json) {
                const blob = new Blob([JSON.stringify(item)], { type: 'application/json' })
                gists.push({
                    'id': item.id,
                    'url': URL.createObjectURL(blob),
                    'to': `/gists/${item.id}`
                })
            }
            if (this.gists == null) this.gists = []
            this.gists = this.gists.concat(gists)
        }
    }
}