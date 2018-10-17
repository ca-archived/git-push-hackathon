export default {
    props: {
        'user': {
            type: String,
            default: 'user'
        }
    },
    template: `<div class='gist-list'>
                    <router-link
                        v-for='gist in gists'
                        v-bind:to='"/gists/" + gist.id'
                        v-if='gists.length > 0'
                    >
                        <gist-item v-bind:url='gist.url'></gist-item>
                    </router-link>
                    <div class='load' v-on:click='load()' v-if='gists.length > 0 && infiniteScroll && !isLast'>さらに読み込む</div>
                    <div class='messeage center' v-if='gists.length == 0'>表示できる gist がまだありません。</div>
                </div>`,
    data: function () {
        return {
            'gists': [],
            'isLast': false,
            'infiniteScroll': false
        }
    },
    created: function () {
        const urls = {
            'user': 'https://api.github.com/gists',
            'public': 'https://api.github.com/gists/public',
            'starred': 'https://api.github.com/gists/starred'
        }

        this.isAuth = 'accessToken' in localStorage
        if (!(this.user in urls)) this.url = urls['public']
        else if (!this.isAuth) this.url = urls['public']
        else this.url = urls[this.user]

        this.infiniteScroll = !('IntersectionObserver' in window && 'MutationObserver' in window)
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
                        this.isLast = !response.headers.has('Link')
                        if (!this.isLast) {
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

                        this.addGist(await response.json())
                    })
            }
        },
        addGist: function (json) {
            const gists = []
            for (let item of json) {
                const blob = new Blob([JSON.stringify(item)], { type: 'application/json' })
                gists.push({
                    'id' : item.id,
                    'url' : URL.createObjectURL(blob)
                })
            }
            this.gists = this.gists.concat(gists)
        }
    }
}