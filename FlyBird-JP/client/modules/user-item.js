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
    props: ['url', 'username'],
    template: `<div class='user-item'>
                    <div class='root' v-if='user != null && log.length == 0'>
                        <a v-bind:href='user.html_url' class='icon'>
                            <img v-bind:data-url='user.avatar_url' v-if='lazyLoad' />
                            <img v-bind:src='user.avatar_url' v-if='!lazyLoad' />
                        </a>
                        <div class='name'>
                            <a v-bind:href='user.html_url'>{{ user.login }}</a>
                        </div>
                        <div class='open'>
                            <slot name='link'><a v-bind:href='user.html_url'>Gistを見る</a></slot>
                        </div>
                    </div>
                    <div class='message center' v-if='log.length > 0'>{{ log }}</div>
                </div>`,
    data: function () {
        return {
            'user': null,
            'lazyLoad': false,
            'log': ''
        }
    },
    created: function () {
        this.lazyLoad = imageObserver != null
        if (this.url != null || this.username != null) {
            const url = this.url || `https://api.github.com/users/${this.username}`
            fetch(url)
                .then((response) => {
                    if (response.ok) {
                        if (this.url.startsWith('blob')) URL.revokeObjectURL(this.url)
                        return response.json()
                    }
                    else throw new Error(`${response.status} ${response.statusText}`)
                })
                .then(this.setUser)
                .catch((err) => {
                    this.log = err.toString()
                })
        } else {
            this.log = '属性が不正です。'
        }
    },
    methods: {
        setUser: function (json) {
            this.user = json

            if (this.lazyLoad) {
                this.$nextTick().then(() => {
                    imageObserver.observe(this.$el.getElementsByTagName('img')[0])
                })
            }
        }
    }
}