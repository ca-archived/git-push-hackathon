const template = `
    <style>
        :host {
             display: block;
             color: #fff;
             height: 100%;
        }
        #login {
            display: block;
            width: 200px;
            height: calc(100% - 1em);
            border:solid 1px #fff;
            border-radius:200px;
            background: transparent;
            color: #fff;
            margin: 0.5em;
            font-size: large;
            cursor: pointer;
            font-weight: 100;
            font-family:"游ゴシック", "YuGothic", "Yu Gothic", 'ヒラギノ角ゴ ProN W3', "Hiragino Kaku Gothic ProN", "Meiryo", Helvetica, Arial, sans-serif;
            outline: 0;
            transition: background ease-in-out 0.3s;
        }
        #login:hover {
            background: #fff;
            color: #333;
            font-weight: 500;
        }
        #login:disabled{
            display: none;
        }
    </style>
    <button id="login" disabled="disabled">Login</button>
`;

class ButtonLogin extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ 'mode': 'open' })
        this.shadowRoot.innerHTML = template
        let accessToken = localStorage.getItem('accessToken')
        const url = new URL(location.href)
        if (accessToken == null) {
            if (url.searchParams.has("code")) {

            }
            else {
                this.shadowRoot.getElementById("login").disabled = false
                this.shadowRoot.getElementById("login").addEventListener("click", (event) => {
                    location.href = "https://github.com/login/oauth/authorize?client_id=a51e816845d833008db2"
                })
            }
        }
    }
    static get observedAttributes() {
        return [];
    }
    connectedCallback() {
    }
}

window.customElements.define('button-login', ButtonLogin)