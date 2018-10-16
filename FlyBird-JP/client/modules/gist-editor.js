const codeMirrorPath = '/codemirror/mode'
const extentions = {
    'js': 'javascript',
    'py': 'python',
    'go': 'go',
    'md': 'markdown'
}

export default {
    props: [],
    template: `<div class='gist-editor' v-if='!isSended'>
                    <input type='text' placeholder='Gist description...' class='desc' v-model="description"></input>
                    <div class='file' v-for="(file, index) in files">
                        <input type='text' placeholder='Filename including extention...' class='filename' v-on:input='loadLang(file.id, $event)' v-model="file.name"></input>
                        <textarea v-bind:class='"editor" + file.id'></textarea>
                        <div v-if='index == files.length - 1 && index > 0' v-on:click='del(file.id)' class='button negative'>Delete</div>
                    </div>
                    <div class='buttons'>
                        <div class='button' v-on:click='add()'>Add file</div>
                        <div>
                            <div class='button' v-if='isPublic' v-on:click='isPublic = !isPublic'>Publicにする</div>
                            <div class='button secret' v-else='v-else' v-on:click='isPublic = !isPublic'>Secretにする</div>
                            <div class='button' v-on:click='send()'>Create gist</div>
                        </div>
                    </div>
                </div>`,
    data: function () {
        return {
            'description': '',
            'files': [],
            'count': 0,
            'isPublic': false,
            'isSended': false
        }
    },
    mounted: function () {
        this.add()
    },
    methods: {
        add: function () {
            this.files.push({
                'id': this.count,
                'name': '',
                'content': '',
                'cm': null
            })
            this.$nextTick().then(() => {
                const textarea = this.$el.getElementsByClassName(`editor${this.count}`)[0]
                let index = -1;
                for (let i = this.files.length - 1; i >= 0; i++) {
                    if (this.files[i].id == this.count) {
                        index = i
                        break
                    }
                }
                this.files[index].cm = CodeMirror.fromTextArea(textarea, {
                    'lineNumbers': true
                })
                this.count++
            })
        },
        del: function (id) {
            for (let file of this.files) {
                if (file.id == id) {
                    file.cm.toTextArea()
                    this.files.splice(this.files.indexOf(file), 1)
                    break;
                }
            }
        },
        loadLang: function (id, event) {
            if (event.target.value.includes('.') && !event.target.value.endsWith('.')) {
                const extention = event.target.value.split('.').slice(-1)[0]
                if (extention in extentions) {
                    const script = document.createElement('script');
                    script.src = `${codeMirrorPath}/${extentions[extention]}/${extentions[extention]}.js`
                    script.addEventListener('load', (event) => {
                        this.files[id].cm.setOption('mode', extentions[extention])
                    })
                    document.body.appendChild(script)
                }
            }
        },
        send: function () {
            const json = {
                'description': this.description,
                'files': {},
                'public': this.isPublic
            }

            for (let file of this.files) {
                const content = file.cm.getValue()
                if (content.length > 0) {
                    json.files[file.name] = {
                        'content': content
                    }
                }
            }

            if (Object.keys(json.files).length > 0) {
                this.isSended = true
                fetch('https://api.github.com/gists', {
                    'method': 'POST',
                    'body': JSON.stringify(json),
                    'headers': new Headers({
                        'Content-type': 'application/json',
                        'Authorization': ` token ${localStorage.getItem('accessToken')}`
                    })
                }).then((response) => {
                    if (response.status == 201) {
                        response.json()
                            .then((json) => {
                                this.$router.push(`/gists/${json.id}`)
                            })
                    } else this.$router.push('/')
                })
            } else {
                document.getElementById('dialog').__vue__.alert('入力内容がありません。', '')
            }
        }
    }
}