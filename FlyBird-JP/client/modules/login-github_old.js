export default class LoginGithub extends HTMLElement {
    get template() {
        return `
        <style>
            :host {
                 display: flex;
                 min-width: 175px;
                 flex-wrap: wrap;
                 height: 100%;
                 box-sizing: border-box;
            }
            :host * {
                box-sizing: inherit;
            }
            #login, #user{
                width: 175px;
                height: 60px;
                display: grid;
                grid-template-columns: calc(60px - 1em) 1fr;
                grid-template-rows: 3fr 2fr;
                align-items: center;
                padding: 0.5em;
                cursor: pointer;
                margin: auto 0;
            }
            .icon{
                grid-column: 1;
                grid-row: 1 / 3;
                border-radius: 50%;
                width: auto;
                height: 100%;
                background: #fff;
            }
            .label {
                grid-column: 2;
                grid-row: 1;
                text-align: center;
            }
            .desc {
                font-size: x-small;
                grid-column: 2;
                grid-row: 2;
                text-align: center;
            }
            #dashboard {
                position: fixed;
                width: 450px;
                height: calc(100% - 60px);
                transform: translateX(450px);
                transition: transform ease-in-out 0.3s;
                background: #fff;
                top: 60px; 
                right: 0;
                z-index: 500;
                color: #000;
                overflow: hidden;
                border: solid 0.5px #ddd;
            }
            #dashboard.visible{
                transform: translateX(0);
            }
            #buttons {
                width: 100%;
                display: flex;
                border-top: solid 0.5px #ddd;
            }
            .button {
                width: 50%;
                height: 4rem;
                text-align: center;
                line-height: 4rem;
                cursor: pointer;
                transition: border-color ease-in-out 0.3s;
                font-weight: 500;
                border: solid 0.5px #ddd;
            }
            #jump:hover {
                border-color: #999;
            }
            #logout {
                color: #f00;
            }
            #logout:hover {
                border-color: #f00;
            }
            #events {
                height: calc(100% - 4rem);
                overflow-y: auto;
                -webkit-overflow-scrolling: touch;
            }
            git-event {
                border-top: solid 0.5px #ddd;
            }
            .disabled {
                display:none!important;
            }
            .center {
                position: absolute;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
                margin: auto;
            }
            .messeage {
                width: 100%;
                height: 2rem;
                line-height: 2rem;
                text-align: center;
                bottom: 4rem;
            }
        </style>
        <div id='login'>
            <img class='icon' src='/images/GitHub-Mark-120px-plus.png' />
            <div class='label'>Github</div>
            <div class='desc'>Githubにログインする</div></div>
        <div id='user'>
            <img class='icon' id='usericon' />
            <div class='label' id='username'></div>
            <div class='desc'>Github</div>
        </div>
        <div id='dashboard'>
            <div id='events'></div>
            <div id='buttons'>
                <div id='jump' class='button'>Github.comで見る</div>
                <div id='logout' class='button'>ログアウトする</div>
            </div>
        </div>
    `
    }

    constructor() {
        super();
        this.attachShadow({ 'mode': 'open' })
        this.shadowRoot.innerHTML = this.template

        this.shadowRoot.getElementById('login').addEventListener('click', (event) => {
            this.login()
        })

        this.shadowRoot.getElementById('logout').addEventListener('click', (event) => {
            this.logout()
        });

        this.accessToken = localStorage.getItem(`accessToken`)
        if (this.accessToken == null) {
            this.shadowRoot.getElementById('user').classList.add('disabled')
        } else {
            this.shadowRoot.getElementById('login').classList.add('disabled')
            this.getUser()
                .then((user) => {
                    this.popup(`ようこそ！${user['login']}さん`, {
                        'body': `Githubにログインしています。`,
                        'icon': user['avatar_url'],
                        'tag': `login?t=${new Date().getUTCSeconds()}`
                    })

                    this.shadowRoot.getElementById('usericon').src = user['avatar_url']
                    this.shadowRoot.getElementById('username').textContent = user['login']

                    this.shadowRoot.getElementById('jump').addEventListener('click', (event) => {
                        location.href = json['html_url']
                    })

                    this.shadowRoot.getElementById('user').addEventListener('click', (event) => {
                        if (this.shadowRoot.getElementById('dashboard').classList.contains('visible')) {
                            this.shadowRoot.getElementById('dashboard').classList.remove('visible')
                        }
                        else {
                            this.shadowRoot.getElementById('dashboard').classList.add('visible')

                            if (this.shadowRoot.getElementById('events').innerHTML.length == 0) {
                                fetch(`${user.url}/received_events`)
                                    .then((response) => response.json())
                                    .then((json => {
                                        if (json.length > 0) {
                                            let html = ''
                                            for (let event of json) {
                                                const blob = new Blob([JSON.stringify(event)], { type: 'application/json' })
                                                const url = URL.createObjectURL(blob)
                                                html += `<git-event url='${url}'></git-event>`;
                                            }
                                            this.shadowRoot.getElementById('events').innerHTML = html
                                        }
                                        else {
                                            this.shadowRoot.getElementById('events').innerHTML = `<div class='messeage center'>表示できるイベントがまだありません。</div>`
                                        }
                                    }))
                            }
                        }
                    })
                })
        }
    }
    static get observedAttributes() {
        return [];
    }
    connectedCallback() {
    }
    attributeChangedCallback(name, oldValue, newValue) {
        if (oldValue != newValue) switch (name) {
            default: break;
        }
    }
    login() {
        location.href = `/auth/github`
    }
    logout() {
        localStorage.clear(`accessToken`)
        location.href = '/'
    }
    popup(title, msg) {
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
    }
    async getUser() {
        const response = await fetch('https://api.github.com/user', { 'headers': { 'Authorization': ` token ${this.accessToken}` } })
        const json = await response.json()
        return json
    }
}