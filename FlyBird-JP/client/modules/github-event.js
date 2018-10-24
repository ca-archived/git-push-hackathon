let imageObserver
if ('IntersectionObserver' in window) {
    imageObserver = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting && 'url' in entry.target.dataset && entry.target.dataset.url.length > 0) {
                entry.target.src = entry.target.dataset.url
                delete entry.target.dataset.url
                imageObserver.unobserve(entry.target)
            }
        })
    })
}

export default {
    props: ['url'],
    template: `<div class='github-event'>
                    <div class='root' v-if='user != null && log.length == 0'>
                        <a v-bind:href='user.page'>
                            <img v-bind:data-url='user.img' v-if='lazyLoad' />
                            <img v-bind:src='user.img' v-if='!lazyLoad' />
                        </a>
                        <a v-bind:href='user.page' class='name'>{{ user.name }}</a>
                        <span class='date'>{{ date | dateFormat }}</span>
                        <span class='action' v-html='action'></span>
                        <span class='target' v-html='target'></span>
                    </div>
                    <div class='message center' v-if='log.length > 0'>{{ log }}</div>
                </div>`,
    data: function () {
        return {
            'user': null,
            'action': '',
            'target': '',
            'date': new Date().toLocaleDateString(),
            'lazyLoad': false,
            'log': ''
        }
    },
    created: function () {
        this.lazyLoad = imageObserver != null
        if (this.url != null) {
            fetch(this.url)
                .then((response) => {
                    if (response.ok) {
                        if (this.url.startsWith('blob')) URL.revokeObjectURL(this.url)
                        return response.json()
                    }
                    else throw new Error(`${response.status} ${response.statusText}`)
                })
                .then(this.setEvent)
                .catch((err) => {
                    this.log = err.toString()
                })
        } else {
            this.log = '属性が不正です。'
        }
    },
    methods: {
        setEvent: function (json) {
            this.user = {
                'name': json.actor.login,
                'page': `https://github.com/${json.actor.login}`,
                'img': json.actor.avatar_url
            }
            this.target = `<a href='https://github.com/${json.repo.name}'  target='_blank'>${json.repo.name}</a>`
            this.date = json.created_at

            switch (json.type) {
                case 'WatchEvent':
                    this.action = 'started'
                    break
                case 'ForkEvent':
                    this.action = `<a href='${json.payload.forkee.html_url}' target='_blank'>forked</a>`
                    break
                case 'CommitCommentEvent':
                    this.action = `${json.payload.action} <a href='${json.payload.comment.html_url}' target='_blank'>comment</a> on commit`
                    break
                case 'IssueCommentEvent':
                    this.action = `${json.payload.action} <a href='${json.payload.comment.html_url}' target='_blank'>comment</a> on <a href='${json.payload.issue.html_url}' target='_blank'>issue</a>`
                    break
                case 'PullRequestEvent':
                    this.action = `${json.payload.action} <a href='${json.payload.pull_request.html_url}'  target='_blank'>pull request</a>`
                    break
                case 'IssuesEvent':
                    this.action = `${json.payload.action} <a href='${json.payload.issue.html_url}' target='_blank'>issue</a>`
                    break
                case 'PushEvent':
                    this.action = `pushed`
                    break
                case 'CreateEvent':
                    this.action = `created ${json.payload.ref_type}`
                    break
                case 'DeleteEvent':
                    this.action = `deleted ${json.payload.ref_type}`
                    break
                case 'PullRequestReviewCommentEvent':
                    this.action = `${json.payload.action} <a href='${json.payload.comment.html_url}' target='_blank'>comment</a> on <a href='${json.payload.pull_request.html_url}' target='_blank'>pull request</a>`
                    break
                case 'ReleaseEvent':
                    this.action = `released`
                    break
                default:
                    this.action = 'did action'
                    break
            }

            if (this.lazyLoad) {
                this.$nextTick().then(() => {
                    imageObserver.observe(this.$el.getElementsByTagName('img')[0])
                })
            }
        }
    }
}