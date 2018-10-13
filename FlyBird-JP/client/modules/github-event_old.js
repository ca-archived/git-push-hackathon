export default class GithubEvent extends HTMLElement {
    get template() {
        return `
        <style>
            :host {
                width: 100%;
                display: grid;
                grid-template-columns: 5.5rem 1fr;
                grid-template-rows: 1.5rem 1.5rem 1.5rem;
                box-sizing: border-box;
                padding: 1rem;
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
            #username, #target, #action {
                text-overflow: ellipsis;
                white-space: nowrap;
                overflow: hidden;
                line-height: 1.5rem;
            }
            #username {
                grid-column: 2;
                grid-row: 1;
            }
            #action {
                font-weight: 500;
            }
            #target {
                grid-column: 2;
                grid-row: 2;
            }
            #date {
                font-size: small;
            }
        </style>
        <img id='usericon' />
        <div>
            <span id='username'></span> <span id='action'></span>
        </div>
        <span id='target'></span>
        <span id='date'></span>
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
        this.shadowRoot.getElementById('usericon').src = json.actor.avatar_url
        this.shadowRoot.getElementById('username').innerHTML = `<a href='https://github.com/${json.actor.login}'>${json.actor.login}</a>`
        console.log(json)

        let action
        switch(json.type){
            case 'WatchEvent':
                action = 'started'
                break
            case 'ForkEvent':
                action = `<a href='${json.payload.forkee.html_url}' target='_blank'>forked</a>`
                break
            case 'CommitCommentEvent':
                action = `${json.payload.action} <a href='${json.payload.comment.html_url}' target='_blank'>comment</a> on commit`
                break
            case 'IssueCommentEvent':
                action = `${json.payload.action} <a href='${json.payload.comment.html_url}' target='_blank'>comment</a> on <a href='${json.payload.issue.html_url}' target='_blank'>issue</a>`
                break
            case 'PullRequestEvent':
                action = `${json.payload.action} <a href='${json.payload.pull_request.html_url}'  target='_blank'>pull request</a>`
                break
            case 'IssuesEvent':
                action = `${json.payload.action} <a href='${json.payload.issue.html_url}' target='_blank'>issue</a>`
                break
            case 'PushEvent':
                action = `pushed`
                break
            case 'CreateEvent':
                action = `created ${json.payload.ref_type}`
                break
            case 'DeleteEvent':
                action = `deleted ${json.payload.ref_type}`
                break
            case 'PullRequestReviewCommentEvent':
                action = `${json.payload.action} <a href='${json.payload.comment.html_url}' target='_blank'>comment</a> on <a href='${json.payload.pull_request.html_url}' target='_blank'>pull request</a>`
                break
            case 'ReleaseEvent':
                action = `released`
                break
            default:
                action = 'did action'
                break
        }
        this.shadowRoot.getElementById('action').innerHTML = action

        this.shadowRoot.getElementById('target').innerHTML = `<a href='https://github.com/${json.repo.name}'  target='_blank'>${json.repo.name}</a>`
        this.shadowRoot.getElementById('date').textContent = new Date(json.created_at).toLocaleDateString('ja-JP', {'year': 'numeric', 'month': 'numeric', 'day': 'numeric', 'hour' : 'numeric', 'minute': 'numeric', 'second': 'numeric'})
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