
Vue.filter('dateFormat', (date) => {
    let differ = (new Date() - new Date(date)) / (1000 * 60)
    if (differ > 24 * 60) return new Date(date).toLocaleDateString()
    else if (differ > 60) return `${Math.floor(differ / 60)}時間前`
    else return `${Math.floor(differ)}分前`
})

export default {
    props: {
        'user': {
            type: String,
            default: 'user'
        }
    },
    template: `<div class='gist-list'>
                    <gist-item
                        v-for="gist in gists"
                        v-bind:url="gist"
                        v-if='gists.length > 0'
                    ></gist-item>
                    <div class='load' v-on:click='load()' v-if='gists.length > 0 &&isShowLoadButton && !isLast'>さらに読み込む</div>
                    <div class='messeage center' v-if='gists.length == 0'>表示できる gist がまだありません。</div>
                </div>`,
    data: function () {
        return {
            'gists': [],
            'isLast': false,
            'isShowLoadButton': false
        }
    },
    created: function () {
        this.url = 'https://api.github.com/gists'
        this.isLast = false
        this.isAuth = 'accessToken' in localStorage
        if (!this.isAuth || this.user == 'public') this.url += '/public'
        else if(this.user == 'starred') this.url += '/starred'

        this.load()
        this.isShowLoadButton = !('IntersectionObserver' in window && 'MutationObserver' in window)
        if ('IntersectionObserver' in window && 'MutationObserver' in window) {
            this.intersectionObserver = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    if ((entry.isIntersecting && this.$el.parentElement.scrollTop > 0)) {
                        this.intersectionObserver.unobserve(entry.target);
                        this.load()
                    }
                });
            });
        }
        this.mutationObserver = new MutationObserver((mutations) => {
            for(let i = mutations.length - 1;i >= 0;i--){
                if(mutations[i].addedNodes.length == 0) continue
                const lastChild = mutations[i].addedNodes[mutations[i].addedNodes.length - 1]
                if ('tagName' in lastChild && lastChild.tagName.toLowerCase() == 'gist-item') {
                    this.intersectionObserver.observe(lastChild);
                    break
                }
                else continue
            }
        });
        this.$nextTick().then(() => {
            this.mutationObserver.observe(this.$el, { 'childList': true });
        })
    },
    methods: {
        load() {
            if (!this.isLast) {
                let promise
                if (this.isAuth) {
                    promise = fetch(this.url, {
                        'headers': { 'Authorization': ` token ${localStorage.getItem('accessToken')}` },
                        'cache': 'no-cache'
                    })
                }
                else {
                    promise = fetch(this.url, {
                        'cache': 'no-cache'
                    })
                }
                promise.then(async (response) => {
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

                    const gists = []
                    for (let item of await response.json()) {
                        const blob = new Blob([JSON.stringify(item)], { type: 'application/json' })
                        gists.push(URL.createObjectURL(blob))
                    }
                    this.gists = this.gists.concat(gists)
                })
            }
        }
    }
}