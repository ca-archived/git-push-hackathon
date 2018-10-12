export default class GistList extends HTMLElement {
    get template() {
        return `
        <style>
            :host {
                 width: 100%;
                 height: 100%;
                 padding: 1rem;
                 overflow-y: auto;
                 -webkit-overflow-scrolling: touch;
                 display: block;
                 box-sizing: border-box;
            }
            :host * {
                box-sizing: inherit;
            }
            #user {
                height: 60px;
                line-height: 60px;
                margin: 0;
                margin-bottom: 1rem;
            }
            #add_gist{
                width: 100%;
                height: 60px;
                line-height: 60px;
                display: block;
                box-sizing: border-box;
                box-shadow: 0 2px 2px rgba(0,0,0, 0.24), 0 0 2px rgba(0,0,0, 0.12);
                border-radius: 5px;
                margin-bottom: 1rem;
                text-align: center;
                cursor: pointer;
            }
            #list {
                height: calc(100% - 120px - 2rem);
                position: relative;
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
        <h2 id='user'></h2>
        <div id='add_gist'>追加する</div>
        <div id='list'></div>
    `
    }

    constructor() {
        super();
        this.attachShadow({ 'mode': 'open' })
        this.shadowRoot.innerHTML = this.template
        this.endpoint = 'https://api.github.com'
        this.linkPattern = /<(http(?:s)?:\/\/(?:[\w-]+\.)+[\w-]+(?:\/[\w-.\/?%&=]*)?)>; rel="(.+?)"/g

        this.intersectionObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if ((entry.isIntersecting && this.scrollTop > 0)) {
                    this.intersectionObserver.unobserve(entry.target);
                    this.load()
                }
            });
        });
        this.mutationObserver = new MutationObserver((mutations) => {
            mutations.forEach((mutation) => {
                const lastChild = mutation.addedNodes[mutation.addedNodes.length - 1];
                if ('tagName' in lastChild && lastChild.tagName.toLowerCase() == 'gist-item') {
                    this.intersectionObserver.observe(lastChild);
                }
            });
        });
        this.mutationObserver.observe(this.shadowRoot.getElementById('list'), { 'childList': true });

        this.load()
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
    load() {
        let url = this.nextUrl || `${this.endpoint}/gists`

        if (url != this.lastUrl) {
            let promise
            if (localStorage.getItem('githubAccessToken') != null) {
                this.shadowRoot.getElementById('user').textContent = 'Your gists'
                promise = fetch(url, {
                    'headers': { 'Authorization': ` token ${localStorage.getItem('githubAccessToken')}` },
                    'cache': 'no-cache'
                })
            }
            else {
                this.shadowRoot.getElementById('user').textContent = 'Public gists'
                promise = fetch(url, {
                    'cache': 'no-cache'
                })
            }
            promise.then(async (response) => {
                const links = response.headers.get('Link')
                const link = {}
                let m
                while ((m = this.linkPattern.exec(links)) != null) {
                    link[m[2]] = m[1]
                }
                this.nextUrl = link.next
                this.lastUrl = link.last

                let html = ''
                for (let item of await response.json()) {
                    const blob = new Blob([JSON.stringify(item)], {type : 'application/json'})
                    const url = URL.createObjectURL(blob)
                    html += `<gist-item url='${url}'></gist-item>`
                }
                if(html.length == 0) html = `<div class='messeage center'>表示できる gist がまだありません。</div>`
                this.shadowRoot.getElementById('list').innerHTML += html
            })
        }
    }
}