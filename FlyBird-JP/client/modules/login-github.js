import MyDialog from '/modules/my-dialog.js'
import EventList from '/modules/event-list.js'
import UserList from '/modules/user-list.js'

export default {
    template: `<div class='login-github'>
                    <div class='button' v-on:click='login()' v-if='user == null'>
                        <img class='icon' src='https://flybird.jp:49650/images/GitHub-Mark-120px-plus.png' />
                        <div class='label'>Github</div>
                        <div class='desc'>Githubにログインする</div>
                    </div>
                    <div class='button' v-on:click='isActive = !isActive' v-if='user != null'>
                        <img class='icon' id='usericon' v-bind:src='user.avatar_url' />
                        <div class='label' id='username'>{{ user.login }}</div>
                        <div class='desc'>Github</div>
                    </div>
                    <div class='dashboard' v-bind:class='{visible:isActive}'>
                        <div class='list' v-if='user != null'>
                            <h2 class='accordion' v-on:click='isShowUsers = !isShowUsers' v-bind:class='{"opened":isShowUsers}'>フォロー中ユーザー</h2>
                            <user-list v-bind:username='user.login' v-show='isShowUsers'></user-list>
                            <event-list v-bind:src='user.received_events_url' v-show='isActive && !isShowUsers'></event-list>
                        </div>
                        <div class='buttons'>
                            <div class='button' v-on:click='jump()'>Github.comで見る</div>
                            <div class='button negative' v-on:click='logout()'>ログアウトする</div>
                        </div>
                        <div v-if='isActive' class='backdrop' v-on:click='isActive = false'></div>
                    </div>
                    <my-dialog ref='dialog'></my-dialog>
                </div>`,
    components: {
        'my-dialog': MyDialog,
        'event-list': EventList,
        'user-list': UserList
    },
    data: function () {
        return {
            'isActive': false,
            'user': null,
            'isShowUsers': false
        }
    },
    created: function () {
        if (this.token != null) {
            this.getUser()
                .then((user) => {
                    this.user = user
                    localStorage.setItem('username', this.user.login)
                    this.popup(`ようこそ！${this.user.login}さん`, {
                        'body': "Githubにログインしています。",
                        'icon': this.user.avatar_url,
                        'tag': `login?date=${new Date().toLocaleDateString()}`
                    })
                })
                .catch((err) => {
                    this.$refs.dialog.alert('エラーが発生しました。', `${err.toString()}\nログアウトします。`, () => {
                        this.logout()
                    })
                })
        }
    },
    methods: {
        login: function () {
            location.href = `/auth/github`
        },
        logout: function () {
            this.$router.push('/logout')
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
            const response = await fetch('https://api.github.com/user', { 'headers': new Headers({ 'Authorization': ` token ${this.token}` }) })
            if (response.status == 401) {
                const json = await response.json()
                throw new Error(`${response.status} ${response.statusText} with ${json.message}`)
            }
            else {
                this.isAuth = true
                return await response.json()
            }
        },
        jump: function () {
            location.href = this.user.html_url
        }
    }
}