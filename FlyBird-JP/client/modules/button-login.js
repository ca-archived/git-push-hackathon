const apis = {
    "github": {
        "endpoint": "https://api.github.com",
        "icon" : "GitHub-Mark-120px-plus.png"
    }
}

const template = `
    <style>
        :host {
             display: flex;
             color: #fff;
             height: 100%;
             max-height: 100px;
             padding: 0.5em;
        }
        a {
            color: inherit;
            text-decoration: none;
            width: 100%;
            height: 100%;
        }
        #login, #logout, a{
            display: grid;
            grid-template-columns: calc(75px - 1em) 1fr;
            grid-template-rows: 3fr 2fr;
            align-items: center;
        }
        #login, #logout, #user{
            width: 175px;
            height: 100%;
            border: solid 1px #fff;
            border-radius: 150px;
            transition: background ease-in-out 0.3s;
            cursor: pointer;
        }
        #login:hover, #logout:hover, #user:hover {
            background: #fff;
            color: #333;
            font-weight: 500;
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
    <div id='login'></div>
    <div id='logout'><div class='label'>Logout</div></div>
    <div id='user'></div>
`;

class ButtonLogin extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ 'mode': 'open' })
        this.shadowRoot.innerHTML = template

        this.service = this.getAttribute('service').toLowerCase()
        if(!this.service in apis) this.service = "github"

        this.shadowRoot.getElementById('login').innerHTML = `<img id='serviceIcon' src='/images/${apis[this.service].icon}' class='icon' /><div class='label'>${this.service.charAt(0).toUpperCase()}${this.service.slice(1)}</div><div class="desc">Authorization</div>`

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
                    const userEl = this.shadowRoot.getElementById('user')
                    userEl.innerHTML = `<a href='${user['page']}'><img src='${user['img']}' class='icon' /><div class='label'>${user['name']}</div><div class='desc'>${this.service.charAt(0).toUpperCase()}${this.service.slice(1)}</div></a>`
                }
            })
        }
    }
    static get observedAttributes() {
        return [];
    }
    async getUser() {
        let user = {}

        switch (this.service) {
            case "github":
                const res = await fetch(`${apis[this.service].endpoint}/user`, { headers: { 'Authorization': ` token ${this.accessToken}` } })
                const json = await res.json()
                user.img = json['avatar_url']
                user.name = json['login']
                user.page = json['html_url']
                break;
            default:
                user = null;
                break;
        }

        return user
    }
    connectedCallback() {
    }
    attributeChangedCallback(name, oldValue, newValue) {
        if (oldValue != newValue) switch (name) {
            default: break;
        }
    }
}

window.customElements.define('button-login', ButtonLogin)