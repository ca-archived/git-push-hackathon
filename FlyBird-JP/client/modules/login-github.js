export default {
    template: `<div class='login-github'>
                    <div class='button' v-on:click='login()' v-if='!isAuth'>
                        <img class='icon' src='/images/GitHub-Mark-120px-plus.png' />
                        <div class='label'>Github</div>
                        <div class='desc'>Githubにログインする</div>
                    </div>
                    <div class='button' v-on:click='showEvents();isActive = !isActive;' v-if='isAuth'>
                        <img class='icon' id='usericon' v-bind:src='user.img' />
                        <div class='label' id='username'>{{ user.name }}</div>
                        <div class='desc'>Github</div>
                    </div>
                    <div class='dashboard' v-bind:class='{visible:isActive}'>
                        <div class='events'>
                            <git-event
                                v-for="event in events"
                                v-bind:url="event"
                                v-if='events.length > 0'
                            ></git-event>
                            <div class='messeage center' v-else='v-else'>表示できるイベントがまだありません。</div>
                        </div>
                        <div class='buttons'>
                            <div class='button' v-on:click='jump()'>Github.comで見る</div>
                            <div class='button negative' v-on:click='logout()'>ログアウトする</div>
                        </div>
                    </div>
                </div>`,
    data: function () {
        return {
            'isActive': false,
            'isAuth': false,
            'user': {
                'name': '',
                'img': '',
                'url': ''
            },
            'events': []
        }
    },
    created: function () {
        this.isAuth = 'accessToken' in localStorage
        if ("IntersectionObserver" in window) {
            this.imageObserver = new IntersectionObserver((entries) => {
                for (let entry of entries) {
                    if (entry.isIntersecting && 'url' in entry.target.dataset) {
                        entry.target.src = entry.target.dataset.url
                        delete entry.target.dataset.url
                        this.imageObserver.unobserve(entry.target)
                    }
                }
            })
        }
        if (this.isAuth) this.getUser().then((user) => {
            this.user = user
        })
    },
    methods: {
        login: function () {
            location.href = `/auth/github`
        },
        logout: function () {
            localStorage.clear(`accessToken`)
            location.href = '/'
        },
        popup: function (title, msg) {
            if ('Notification' in window) {
                switch (Notification.permission) {
                    case 'granted':
                        const popup = new Notification(title, msg);
                        popup.addEventListener('click', (event) => {
                            window.focus();
                            popup.close();
                        });
                        break;
                    case 'denied':
                        break;
                    case 'default':
                        Notification.requestPermission((permission) => {
                            if (permission == 'granted') {
                                const popup = new Notification(title, msg);
                                popup.addEventListener('click', (event) => {
                                    window.focus();
                                    popup.close();
                                });
                            }
                        });
                        break;
                }
            }
        },
        getUser: async function () {
            const response = await fetch('https://api.github.com/user', { 'headers': { 'Authorization': ` token ${localStorage.getItem('accessToken')}` } })
            const json = await response.json()
            return {
                'name': json.login,
                'img': json.avatar_url,
                'url': json.html_url
            }
        },
        showEvents: function () {
            if (this.events.length == 0) {
                fetch(/*`${this.user.url}/received_events`*/'https://api.github.com/users/leeyh0216/received_events')
                    .then((response) => response.json())
                    .then((json => {
                        const events = []
                        for (let event of json) {
                            const blob = new Blob([JSON.stringify(event)], { type: 'application/json' })
                            events.push(URL.createObjectURL(blob))
                        }
                        this.events = this.events.concat(events)

                        this.$nextTick().then(() => {
                            if ("IntersectionObserver" in window) {
                                for (let img of this.$el.getElementsByTagName('img')) {
                                    if ('url' in img.dataset) this.imageObserver.observe(img)
                                }
                            }
                        })
                    }))
            }
        },
        jump: function () {
            location.href = this.user.url
        }
    }
}