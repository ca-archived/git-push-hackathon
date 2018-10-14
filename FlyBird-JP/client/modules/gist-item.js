
Vue.filter('dateFormat', (date) => {
    let differ = (new Date() - new Date(date)) / (1000 * 60)
    if (differ > 24 * 60) return new Date(date).toLocaleDateString()
    else if (differ > 60) return `${Math.floor(differ / 60)}時間前`
    else return `${Math.floor(differ)}分前`
})

export default {
    props: ['url'],
    template: `<div class='gist-item' v-on:click='jump()'>
                    <img v-bind:data-url='user.img' />
                    <span class='name_and_fiels'>{{ user.name }} / {{ files }}</span>
                    <span class='desc'>{{ desc }}</span>
                    <span class='date'>{{ date | dateFormat }}</span>
                </div>`,
    data: function () {
        return {
            'user': {
                'img':'',
                'name' : ''
            },
            'files' : '',
            'desc' : 'No description.',
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
        jump: function (){
            if(this.id != null) this.$router.push(`/gists/${this.id}`)
        },
        print: function (json) {
            this.user =  {
               'name' : json.owner.login,
               'img' : json.owner.avatar_url
            }
            this.files = Object.keys(json.files).join(', ')
            this.desc = json.description || 'No description.'
            this.date = json.updated_at
            this.id = json.id

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