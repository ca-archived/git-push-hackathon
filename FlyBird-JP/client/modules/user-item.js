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
    props: ['url'],
    template: `<div class='user-item'>
                    <div class='root' v-if='url != null && user != null'>
                        <a v-bind:href='user.html_url' class='icon'>
                            <img v-bind:data-url='user.avatar_url' v-if='lazyLoad' />
                            <img v-bind:src='user.avatar_url' v-if='!lazyLoad' />
                        </a>
                        <div class='name'>
                            <a v-bind:href='user.html_url'>{{ user.login }}</a>
                        </div>
                        <div class='open'>
                            <router-link v-bind:to='"/users/" + user.login + "/gists"'>Gistを見る</router-link>
                        </div>
                    </div>
                </div>`,
    data: function () {
        return {
            'user': null,
            'lazyLoad': false
        }
    },
    created: function () {
        this.lazyLoad = imageObserver != null
        if (this.url != null) {
            fetch(this.url)
                .then((response) => {
                    if (this.url.startsWith('blob')) URL.revokeObjectURL(this.url)
                    return response.json()
                })
                .then(this.setUser)
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