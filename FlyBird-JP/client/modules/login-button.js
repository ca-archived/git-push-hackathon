const apis = {
    'github': {
        'name': 'Github',
        'endpoint': 'https://api.github.com',
        'icon': '/images/GitHub-Mark-120px-plus.png'
    }
}
export default class LoginButton extends HTMLElement {
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
                position: absolute;
                width: 450px;
                height: calc(100% - 60px);
                background: #fff;
                top: 60px; 
                right: 0;
                border:solid 0.5px #ddd;
                display: none;
                z-index: 500;
                color: #000;
                overflow: hidden;
            }
            #logout {
                text-align: center;
                border: solid 0.5px #ddd;
                line-height: 2rem;
                border-radius: 10px;
                margin: 1rem;
                cursor: pointer;
                transition: border-color ease-in-out 0.3s;
            }
            #events {
                height: calc(100% - 4rem);
                overflow-y: auto;
                -webkit-overflow-scrolling: touch;
            }
            #logout:hover {
                border-color: #999;
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
            }
        </style>
        <div id='login'>
            <img class='icon serviceicon' />
            <div class='label servicename'></div>
            <div class='desc'>にログインする</div></div>
        <div id='user'>
            <img class='icon' id='usericon' />
            <div class='label' id='username'></div>
            <div class='desc servicename'></div>
        </div>
        <div id='dashboard'>
            <div id='events'></div>
            <div id='logout'>ログアウトする</div>
        </div>
    `
    }

    constructor() {
        super();
        this.attachShadow({ 'mode': 'open' })
        this.shadowRoot.innerHTML = this.template

        this.service = this.getAttribute('service')
        if (this.service == null || !this.service in apis) this.service = 'github'

        for (let dom of this.shadowRoot.querySelectorAll('.serviceicon')) {
            dom.src = apis[this.service].icon
        }
        for (let dom of this.shadowRoot.querySelectorAll('.servicename')) {
            dom.textContent = apis[this.service].name
        }
        this.shadowRoot.getElementById('user').addEventListener('click', (event) => {
            if (this.shadowRoot.getElementById('dashboard').style.display == 'block') {
                this.shadowRoot.getElementById('dashboard').style.display = 'none'
            }
            else {
                this.shadowRoot.getElementById('dashboard').style.display = 'block'
            }
        })
        this.shadowRoot.getElementById('logout').addEventListener('click', (event) => {
            this.logout()
        });

        this.login()
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
        this.accessToken = localStorage.getItem(`${this.service}AccessToken`)
        if (this.accessToken == null) {
            this.shadowRoot.getElementById('user').classList.add('disabled')

            this.shadowRoot.getElementById('login').addEventListener('click', (event) => {
                location.href = `/auth?service=${this.getAttribute('service')}`
            })
        } else {
            this.shadowRoot.getElementById('login').classList.add('disabled')

            this.getUser()
        }
    }
    logout() {
        localStorage.clear(`${this.service}AccessToken`)
        location.href = '/'
    }
    async getUser() {
        let user = {}

        switch (this.service) {
            case 'github':
                const res = await fetch(`${apis[this.service].endpoint}/user`, { headers: { 'Authorization': ` token ${this.accessToken}` } })
                const json = await res.json()
                user.icon = json['avatar_url']
                user.name = json['login']
                user.page = json['html_url']

                const events = await fetch(`${apis[this.service].endpoint}/users/${user.name}/received_events`)
                let html = ''
                for(let event of await events.json()){
                    const blob = new Blob([JSON.stringify(event)], {type : 'application/json'})
                    const url = URL.createObjectURL(blob)
                    html += `<git-event url='${url}'></git-event>`;
                }
                if(html.length == 0) html = `<div class='messeage center'>表示できるイベントがまだありません。</div>`
                this.shadowRoot.getElementById('events').innerHTML = html
                break;
            default:
                user = null;
                break;
        }
        if (user != null) {
            this.shadowRoot.getElementById('usericon').src = user['icon']
            this.shadowRoot.getElementById('username').textContent = user['name']

            if ('Notification' in window) {
                const title = `ようこそ！${user['name']}さん`
                const msg = {
                    'body': `${apis[this.service].name}にログインしています。`,
                    'icon': user['icon'],
                    'tag': 'login',
                    'sticky': true
                }
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
    }
}