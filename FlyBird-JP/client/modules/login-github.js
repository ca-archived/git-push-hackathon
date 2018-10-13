export default {
    props: {
        username: { type: String, default: 'username' }
    },
    template: `<div class='login-github'>
                    <div class='button' v-on:click='login()' v-if='!isAuth'>
                        <img class='icon' src='/images/GitHub-Mark-120px-plus.png' />
                        <div class='label'>Github</div>
                        <div class='desc'>Githubにログインする</div>
                    </div>
                    <div class='button' v-on:click='showEvents();isActive = !isActive;' v-if='isAuth'>
                        <img class='icon' id='usericon' v-bind:src="user.img" />
                        <div class='label' id='username'>{{ user.name }}</div>
                        <div class='desc'>Github</div>
                    </div>
                    <div class='dashboard' v-bind:class='{visible:isActive}'>
                        <div class='events'>
                            <git-event v-if='events.length > 0'></git-event>
                            <div class='messeage center' v-else='v-else'>表示できるイベントがまだありません。</div>
                        </div>
                        <div class='buttons'>
                            <div class='button'>Github.comで見る</div>
                            <div class='button negative' v-on:click='logout()'>ログアウトする</div>
                        </div>
                    </div>
                </div>`,
    data: function () {
        return {
            'isActive': false,
            'isAuth' : false,
            'user' : {
                'name' : '',
                'img' : ''
            },
            'events' : []
        }
    },
    created : function() {
        this.accessToken = localStorage.getItem(`githubAccessToken`)
        this.isAuth = this.accessToken != null
        if(this.isAuth) this.getUser().then((user) => {
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
                'name' : json.login,
                'img' : json.avatar_url,
                'url' : json.url
            }
        },
        showEvents: function () {
            console.log(this.user.url)
            fetch(`${this.user.url}/received_events`)
                .then((response) => response.json())
                .then((json => {
                    this.events = json
                }))
        }
    }
}