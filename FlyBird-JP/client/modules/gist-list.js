
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
                    <div class='messeage center' v-else='v-else'>表示できる gist がまだありません。</div>
                </div>`,
    data: function () {
        return {
            'gists': [],
            'isAuth': false,
            'user' : 'public'
        }
    },
    created: function () {
        /*
        this.intersectionObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if ((entry.isIntersecting && this.scrollTop > 0)) {
                    this.intersectionObserver.unobserve(entry.target);
                    this.load()
                }
            });
        });
        this.mutationObserver = new MutationObserver((mutations) => {
            mutations.forEach((mutation) => {
                const lastChild = mutation.addedNodes[mutation.addedNodes.length - 1];
                if ('tagName' in lastChild && lastChild.tagName.toLowerCase() == 'gist-item') {
                    this.intersectionObserver.observe(lastChild);
                }
            });
        });
        this.mutationObserver.observe(this.$el.getElementById('list'), { 'childList': true });*/
        this.url = 'https://api.github.com/gists'
        this.isLast = false
        this.isAuth = localStorage.getItem('githubAccessToken') != null
        if (!this.isAuth || this.user == 'public') this.url += '/public'

        this.load()
    },
    methods: {
        load() {
            if (!this.isLast) {
                let promise
                if (this.isAuth) {
                    promise = fetch(this.url, {
                        'headers': { 'Authorization': ` token ${localStorage.getItem('githubAccessToken')}` },
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
                            if(match[2] == 'next') {
                                this.url = match[1]
                                break;
                            }
                        }
                    }

                    for (let item of await response.json()) {
                        const blob = new Blob([JSON.stringify(item)], { type: 'application/json' })
                        this.gists.push(URL.createObjectURL(blob))
                    }
                })
            }
        }
    }
}