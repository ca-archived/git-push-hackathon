export default class GistList extends HTMLElement {
    get template() {
        return `
        <style>
            :host {
                 width: 100%;
                 height: 100%;
                 overflow-y: auto;
                 -webkit-overflow-scrolling: touch;
                 display: block;
                 box-sizing: border-box;
            }
            :host * {
                box-sizing: inherit;
            }
            .tab_area {
                width: 100%;
                height: 60px;
                background: #fafbfc;
                border-bottom: solid 1px #ddd;
            }
            .tabs{
                display: flex;
                align-items: flex-end;
                width:100%;
                max-width: 1000px;
                height: 100%;
                margin: 0 auto;
                transform: translateY(1px);
            }
            .tab.active{
                border: solid 1px #ddd;
                border-bottom: none;
                border-top: solid 3px #ff9800;
                border-radius: 5px 5px 0 0;
                background: #fff;
                color: #333;
            }
            .tab {
                height: 40px;
                padding: 0 1rem;
                line-height: 39px;
                margin: 0;
                margin-left: 1rem;
                font-weight: 500;
                font-size: large;
                cursor: pointer;
                color: #666;
            }
            #add_gist{
                width: calc(100% - 2rem);
                max-width: calc(1000px - 2rem);
                height: 60px;
                line-height: 60px;
                display: block;
                box-sizing: border-box;
                box-shadow: 0 2px 2px rgba(0,0,0, 0.24), 0 0 2px rgba(0,0,0, 0.12);
                border-radius: 5px;
                margin: 1rem auto;
                text-align: center;
                cursor: pointer;
            }
            #list {
                padding: 1rem;
                height: calc(100% - 120px - 2rem);
                position: relative;
                max-width: 1000px;
                margin: 0 auto;
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
        <div class='tab_area'>
            <div class='tabs'>
                <h2 id='user' class='tab'>Yours</h2>
                <h2 id='public' class='tab'>Public</h2>
            </div>
        </div>
        <div id='add_gist'>追加する</div>
        <div id='list'></div>
    `
    }

    constructor() {
        super();
        this.attachShadow({ 'mode': 'open' })
        this.shadowRoot.innerHTML = this.template
        this.endpoint = 'https://api.github.com'
        this.link = {}
        this.linkPattern = /<(http(?:s)?:\/\/(?:[\w-]+\.)+[\w-]+(?:\/[\w-.\/?%&=]*)?)>; rel="(.+?)"/g
        this.isLast = false

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
        let url = this.link['next'] || `${this.endpoint}/gists`

        if (!this.isLast) {
            let promise
            if (localStorage.getItem('githubAccessToken') != null) {
                this.shadowRoot.getElementById('user').classList.add('active')
                promise = fetch(url, {
                    'headers': { 'Authorization': ` token ${localStorage.getItem('githubAccessToken')}` },
                    'cache': 'no-cache'
                })
            }
            else {
                this.shadowRoot.getElementById('public').classList.add('active')
                promise = fetch(url, {
                    'cache': 'no-cache'
                })
            }
            promise.then(async (response) => {
                this.isLast = !response.headers.has('Link')
                if (!this.isLast) {
                    const links = response.headers.get('Link')
                    let m
                    while ((m = this.linkPattern.exec(links)) != null) {
                        this.link[m[2]] = m[1]
                    }
                }

                let html = ''
                for (let item of await response.json()) {
                    const blob = new Blob([JSON.stringify(item)], { type: 'application/json' })
                    const url = URL.createObjectURL(blob)
                    html += `<gist-item url='${url}'></gist-item>`
                }
                if (html.length == 0) html = `<div class='messeage center'>表示できる gist がまだありません。</div>`
                this.shadowRoot.getElementById('list').innerHTML += html
            })
        }
    }
}