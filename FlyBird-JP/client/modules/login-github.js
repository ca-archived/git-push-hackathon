export default {
    template: `<div class='login-github'>
                    <div class='button' v-on:click='login()' v-if='user == null'>
                        <img class='icon' src='/images/GitHub-Mark-120px-plus.png' />
                        <div class='label'>Github</div>
                        <div class='desc'>Githubにログインする</div>
                    </div>
                    <div class='button' v-on:click='showEvents();isActive = !isActive;' v-if='user != null'>
                        <img class='icon' id='usericon' v-bind:src='user.avatar_url' />
                        <div class='label' id='username'>{{ user.login }}</div>
                        <div class='desc'>Github</div>
                    </div>
                    <div class='dashboard' v-bind:class='{visible:isActive}'>
                        <div class='events'>
                            <git-event
                                v-for='event in events'
                                v-bind:url='event'
                            ></git-event>
                            <div class='messeage center' v-if='events != null && events.length == 0'>表示できるイベントがまだありません。</div>
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
            'user': null,
            'events': null
        }
    },
    created: function () {
        if ('accessToken' in localStorage) {
            this.getUser().then((user) => {
                this.user = user
                localStorage.setItem('username', this.user.login)
                this.popup(`ようこそ！${this.user.login}さん`, {
                    "body": "Githubにログインしています。",
                    "icon": this.user.avatar_url,
                    "tag": "login"
                })
            })
        }
    },
    methods: {
        login: function () {
            location.href = `/auth/github`
        },
        logout: function () {
            localStorage.clear(`accessToken`)
            localStorage.clear(`username`)
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
            if (response.status == 401) this.logout()
            else {
                this.isAuth = true
                return response.json()
            }
        },
        showEvents: function () {
            if (this.events == null) {
                fetch(this.user.received_events_url, {
                    headers: new Headers({ 'Authorization': ` token ${localStorage.getItem('accessToken')}` })
                })
                    .then((response) => response.json())
                    .then((json) => {
                        const events = []
                        for (let event of json) {
                            const blob = new Blob([JSON.stringify(event)], { type: 'application/json' })
                            events.push(URL.createObjectURL(blob))
                        }
                        this.events = events
                    })
            }
        },
        jump: function () {
            location.href = this.user.url
        }
    }
}