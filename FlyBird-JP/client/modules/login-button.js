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
            a {
                color: inherit;
                text-decoration: none;
                width: 100%;
                height: 100%;
            }
            #login, #logout, a{
                display: grid;
                grid-template-columns: calc(60px - 1em) 1fr;
                grid-template-rows: 3fr 2fr;
                align-items: center;
            }
            #login, #logout, #user{
                width: 175px;
                height: 60px;
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
            .disabled{
                display:none!important;
            }
        </style>
        <div id='login'>
            <img class='icon serviceicon' />
            <div class='label servicename'></div>
            <div class='desc'>ログインする</div></div>
        <div id='logout'><div class='label'>Logout</div></div>
        <div id='user'>
            <a href='' id='userpage'>
                <img class='icon' id='usericon' />
                <div class='label' id='username'></div>
                <div class='desc servicename'></div>
            </a>
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
            this.shadowRoot.getElementById('logout').classList.add('disabled')

            this.shadowRoot.getElementById('login').addEventListener('click', (event) => {
                location.href = `/auth?service=${this.getAttribute('service')}`
            })
        } else {
            this.shadowRoot.getElementById('login').classList.add('disabled')
            this.shadowRoot.getElementById('logout').classList.add('disabled')

            this.shadowRoot.getElementById('logout').addEventListener('click', (event) => {
                localStorage.clear(`${service}AccessToken`)
                location.href = '/'
            })

            this.getUser().then((user) => {
                if (user != null) {
                    this.shadowRoot.getElementById('usericon').src = user['icon']
                    this.shadowRoot.getElementById('userpage').href = user['page']
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
            })
        }
    }
    logout(){
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
                break;
            default:
                user = null;
                break;
        }

        return user
    }
}