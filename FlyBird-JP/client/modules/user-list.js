import MyDialog from '/modules/my-dialog.js'
import UserItem from '/modules/user-item.js'

export default {
    props: {
        'url': String,
        'username': String,
        'type': {
            type: String,
            default: 'following'
        },
        'limit': {
            type: Number,
            default: 30
        }
    },
    template: `<div class='user-list'>
                    <div class='root' v-if='log.length == 0'>
                        <user-item 
                            v-for='user in users'
                            v-bind:key='user.id'
                            v-bind:url='user.url'
                        >
                            <router-link v-bind:to='"/users/" + user.name + "/gists"' slot='link'>Gistを見る</router-link>
                        </user-item>
                        <button class='load' v-on:click='print(nextUrl)' v-if='users != null && users.length > 0 && !infiniteScroll && nextUrl != null'>さらに読み込む</button>
                    </div>
                    <div class='message center' v-if='log.length > 0'>{{ log }}</div>
                    <my-dialog ref='dialog'></my-dialog>
                </div>`,
    components: {
        'my-dialog': MyDialog,
        'user-item': UserItem
    },
    data: function () {
        return {
            'users': null,
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
                        if ('classList' in lastChild && lastChild.classList.contains('user-item')) {
                            this.intersectionObserver.observe(lastChild);
                            break
                        }
                    }
                }
            })).observe(this.$el.getElementsByClassName('root')[0], { 'childList': true })
        }
        if (this.username != null) this.print(this.getUrl())
        else {
            this.$refs.dialog.prompt('ユーザー名を入力してください。', '', (input) => {
                this.username = input
            })
        }
    },
    methods: {
        print: function (url) {
            if (url != null) {
                this.load(url)
                    .then(this.addUser)
                    .catch((err) => {
                        this.$refs.dialog.alert('エラーが発生しました。', err.toString())
                        this.log = err.toString()
                    })
            }
        },
        getUrl: function () {
            if (this.url != null || this.username != null) {
                const types = ['following', 'followers']
                let type = this.type
                if (!types.includes(type)) type = 'following'

                return this.url || `https://api.github.com/users/${this.username}/${type}?per_page=${this.limit}`
            }
            else throw new Error('属性が不正です。')
        },
        load: async function (url) {
            const headers = new Headers()
            if (this.token != null) {
                headers.append('Authorization', ` token ${this.token}`)
            }
            const response = await fetch(url, { 'headers': headers })
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
        addUser: function (json) {
            const users = []
            for (let item of json) {
                const blob = new Blob([JSON.stringify(item)], { type: 'application/json' })
                users.push({
                    'id': item.id,
                    'name': item.login,
                    'url': URL.createObjectURL(blob)
                })
            }
            if (this.users == null) this.users = []
            this.users = this.users.concat(users)
            if (this.users.length == 0) this.log = '表示できるユーザーは今のところいません。'
        }
    }
}