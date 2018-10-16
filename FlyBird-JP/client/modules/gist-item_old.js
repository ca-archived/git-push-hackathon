export default class GistItem extends HTMLElement {
    get template() {
        return `
        <style>
            :host {
                width: 100%;
                display: grid;
                grid-template-columns: 6rem 1fr;
                grid-template-rows: 1.5rem 1.5rem 1.5rem;
                box-sizing: border-box;
                border: solid 0.5px #ddd;
                border-radius: 5px;
                padding: 1rem;
                margin-bottom: 1rem;
            }
            :host * {
                box-sizing: inherit;
            }
            a {
                color: #2196f3;
            }
            #user {
                display: grid;
            }
            #usericon {
                width: auto;
                height: 100%;
                border-radius: 50%;
                grid-row: 1 / -1;
                text-align: center;
                align-items: center;
                grid-column: 1;
            }
            #files, #desc, #username {
                text-overflow: ellipsis;
                white-space: nowrap;
                overflow: hidden;
                line-height: 1.5rem;
            }
            #files {
                grid-column: 2;
                grid-row: 1;
            }
            #desc {
                grid-column: 2;
                grid-row: 2;
            }
            #username {
                grid-column: 2;
                grid-row: 3;
            }
        </style>
        <img id='usericon' />
        <span id='files'></span>
        <span id='desc'></span>
        <span id='username'></span>
        <span id='username'></span>
    `
    }

    constructor() {
        super();
        this.attachShadow({ 'mode': 'open' })
        this.shadowRoot.innerHTML = this.template
    }
    static get observedAttributes() {
        return ['url'];
    }
    print(json) {
        let files = []
        for (let fileName in json.files) {
            files.push(`<a href='${json.files[fileName].raw_url}'>${json.files[fileName].filename}</a>`)
        }

        this.shadowRoot.getElementById('files').innerHTML = files.join(', ')
        if ('description' in json && json.description.length > 0) {
            this.shadowRoot.getElementById('desc').textContent = json.description
        }
        else this.shadowRoot.getElementById('desc').textContent = 'No description.'
        this.shadowRoot.getElementById('usericon').src = json.owner.avatar_url
        this.shadowRoot.getElementById('username').innerHTML = `by <a href='${json.owner.html_url}'>${json.owner.login}</a>`
    }
    connectedCallback() {
    }
    attributeChangedCallback(name, oldValue, newValue) {
        if (oldValue != newValue) switch (name) {
            case 'url':
                fetch(newValue)
                    .then((response) => response.json())
                    .then((json) => {
                        this.print(json)
                    })
                break;
            default: break;
        }
    }
}