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
    template: `<div class='gist-item' v-if='url != null'>
                    <img v-bind:data-url='user.img' v-if='lazyLoad' />
                    <img v-bind:src='user.img' v-if='!lazyLoad' />
                    <span class='name_and_files'>{{ user.name }} / {{ files }}</span>
                    <span class='desc'>{{ desc }}</span>
                    <span class='date'>{{ date | dateFormat }}</span>
                    <span class='comments'>コメント:{{ comments }}件</span>
                </div>`,
    data: function () {
        return {
            'user': {
                'img': '',
                'name': ''
            },
            'files': '',
            'desc': 'No description.',
            'date': new Date().toLocaleDateString(),
            'comments': 0,
            'lazyLoad' : false
        }
    },
    created: function () {
        this.lazyLoad = imageObserver != null
        if (this.url != null) {
            fetch(this.url)
                .then((response) => response.json())
                .then((json) => {
                    this.setGist(json)
                    if (this.url.startsWith('blob')) URL.revokeObjectURL(this.url)
                })
        }
    },
    methods: {
        setGist: function (json) {
            this.user = {
                'name': json.owner.login,
                'img': json.owner.avatar_url
            }
            this.files = Object.keys(json.files).join(', ')
            this.desc = json.description || 'No description.'
            this.date = json.created_at
            this.comments = json.comments

            if(this.lazyLoad) {
                imageObserver.observe(this.$el.getElementsByTagName('img')[0])
            }
        }
    }
}