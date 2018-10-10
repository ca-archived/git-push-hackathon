const template = `
    <style>
        :host {
             display: block;
             color: #fff;
             height: 100%;
        }
        #login,#logout {
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
            font-family:'游ゴシック', 'YuGothic', 'Yu Gothic', 'ヒラギノ角ゴ ProN W3', 'Hiragino Kaku Gothic ProN', 'Meiryo', Helvetica, Arial, sans-serif;
            outline: 0;
            transition: background ease-in-out 0.3s;
        }
        #login:hover,#logout:hover {
            background: #fff;
            color: #333;
            font-weight: 500;
        }
        #login:disabled, #logout:disabled {
            display: none;
        }
    </style>
    <button id='login' disabled='disabled'>Login</button>
    <button id='logout' disabled='disabled'>Logout</button>
`;

class ButtonLogin extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ 'mode': 'open' })
        this.shadowRoot.innerHTML = template
        let accessToken = localStorage.getItem('accessToken')
        if (accessToken == null) {
            this.shadowRoot.getElementById('login').disabled = false
            this.shadowRoot.getElementById('login').addEventListener('click', (event) => {
                location.href = "/auth"
            })
        } else {
            this.shadowRoot.getElementById('logout').disabled = false
            this.shadowRoot.getElementById('logout').addEventListener('click', (event) => {
                localStorage.clear('accessToken')
                location.href = new URL(location.href).origin
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
            default:break;
        }
    }
}

window.customElements.define('button-login', ButtonLogin)