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
                'url' : ''
            },
            'events': []
        }
    },
    created: function () {
        this.accessToken = localStorage.getItem(`githubAccessToken`)
        this.isAuth = this.accessToken != null
        if (this.isAuth) this.getUser().then((user) => {
            this.user = user
        })
    },
    methods: {
        login: function () {
            location.href = `/auth/github`
        },
        logout: function () {
            localStorage.clear(`githubAccessToken`)
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
            const response = await fetch('https://api.github.com/user', { 'headers': { 'Authorization': ` token ${this.accessToken}` } })
            const json = await response.json()
            return {
                'name': json.login,
                'img': json.avatar_url,
                'url': json.html_url
            }
        },
        showEvents: function () {
            fetch(/*`${this.user.url}/received_events`*/'https://api.github.com/users/leeyh0216/received_events')
                .then((response) => response.json())
                .then((json => {
                    let events = []
                    for (let event of json) {
                        const blob = new Blob([JSON.stringify(event)], { type: 'application/json' })
                        events.push(URL.createObjectURL(blob))
                    }
                    this.events = events

                    let eventsDom = this.$el.getElementsByClassName('events')[0]
                    this.$nextTick().then(() => {
                        if ("IntersectionObserver" in window) {
                            const imageObserver = new IntersectionObserver((entries) => {
                                for (let entry of entries) {
                                    if (entry.isIntersecting) {
                                        entry.target.src = entry.target.dataset.url
                                        delete entry.target.dataset.url
                                        imageObserver.unobserve(entry.target)
                                    }
                                }
                            })

                            for (let img of eventsDom.getElementsByTagName('img')) {
                                if('url' in img.dataset) imageObserver.observe(img)
                            }
                        }
                        else {
                            for (let img of eventsDom.getElementsByTagName('img')) {
                                if('url' in img.dataset) img.src = img.dataset.url
                            }
                        }
                    })
                }))
        },
        jump: function() {
            location.href = this.user.url
        }
    }
}