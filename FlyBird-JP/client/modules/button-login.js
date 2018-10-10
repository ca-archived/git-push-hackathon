const endpoint = 'https://api.github.com'
const template = `
    <style>
        :host {
             display: flex;
             justify-content: flex-end;
             color: #fff;
             height: 100%;
        }
        a {
            color: inherit;
            text-decoration: none;
            display: grid;
            grid-template-rows: 100%;
            align-items: center;
            width: 100%;
            height: 100%;
        }
        #login, #logout{
            display: grid;
            grid-template-rows: 100%;
            align-items: center;
        }
        #login, #logout, #user{
            width: 150px;
            height: calc(100% - 1em);
            border: solid 1px #fff;
            border-radius: 150px;
            margin: 0.5em;
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
            grid-row: 1;
            border-radius: 50%;
            width: auto;
            height: 100%;
        }
        .label {
            grid-column: 2;
            grid-row: 1;
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

        const allowServices = ["github", "twitter"]
        this.service = this.getAttribute('service') || allowServices[0]

        this.shadowRoot.getElementById('login').innerHTML = `<img id='serviceIcon' src='/test.jpg' class='icon' /><div class='label'>${this.service.charAt(0).toUpperCase()}${this.service.slice(1).toLowerCase()}</div>`
        
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
                const userEl = this.shadowRoot.getElementById('user')
                userEl.innerHTML = `<a href='${user['page']}'><img src='${user['img']}' class='icon' /><div class='label'>${user['name']}</div></a>`
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
                const res = await fetch(`${endpoint}/user`, { headers: { 'Authorization': ` token ${this.accessToken}` } })
                const json = await res.json()
                user.img = json['avatar_url']
                user.name = json['login']
                user.page = json['html_url']
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