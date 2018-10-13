
Vue.filter('dateFormat', (date) => {
    let differ = (new Date() - new Date(date)) / (1000 * 60)
    if (differ > 24 * 60) return new Date(date).toLocaleDateString()
    else if (differ > 60) return `${Math.floor(differ / 60)}時間前`
    else return `${Math.floor(differ)}分前`
})

export default {
    props: ['url'],
    template: `<div class='github-event'>
                    <img v-bind:data-url='user.img' />
                    <div>
                        <a v-bind:url='user.page'>{{ user.name }}</a>&nbsp;<span class='action' v-html='action'></span>
                    </div>
                    <span class='target' v-html='target'></span>
                    <span class='date'>{{ date | dateFormat }}</span>
                </div>`,
    data: function () {
        return {
            'user': {
                'name': '',
                'img': '',
                'page': '',

            },
            'action': '',
            'target': '',
            'date': new Date().toLocaleDateString()
        }
    },
    created: function () {
        if (this.url != null) {
            fetch(this.url)
                .then((response) => response.json())
                .then((json) => {
                    this.print(json)
                })
        }
    },
    methods: {
        print: function (json) {
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

            if (!("IntersectionObserver" in window)) {
                this.$nextTick().then(() => {
                    for (let img of this.$el.getElementsByTagName('img')) {
                        if ('url' in img.dataset) img.src = img.dataset.url
                    }
                })
            }
        }
    }
}