import MyDialog from '/modules/my-dialog.js'

const codeMirrorPath = 'https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.40.2'
const extentions = {
    'js': {
        'mode': 'javascript',
        'mime': 'text/javascript'
    },
    'py': {
        'mode': 'python',
        'mime': 'text/x-python'
    },
    'go': {
        'mode': 'go',
        'mime': 'text/x-go'
    },
    'md': {
        'mode': 'markdown',
        'mime': 'text/x-markdown'
    },
    'html': {
        'mode': 'htmlmixed',
        'mime': 'text/html'
    },
    'css': {
        'mode': 'css',
        'mime': 'text/css'
    },
    'java': {
        'mode': 'clike',
        'mime': 'text/x-java'
    }
}

const link = document.createElement('link')
link.rel = 'stylesheet'
link.href = `${codeMirrorPath}/codemirror.min.css`
document.body.appendChild(link)
const script = document.createElement('script')
script.src = `${codeMirrorPath}/codemirror.min.js`
document.body.appendChild(script)

export default {
    template: `<div class='gist-editor' v-if='token != null'>
                    <div class='editor' v-if='!isSended'>
                        <input type='text' placeholder='Gist description...' class='desc' v-model="description"></input>
                        <div class='file' v-for="file in files" v-bind:key='file.id'>
                            <input type='text' placeholder='Filename including extention...' class='filename' v-on:input='loadMode(file.id, $event)' v-model="file.name"></input>
                            <textarea v-bind:class='"editor" + file.id'></textarea>
                            <div v-if='files.length > 1' v-on:click='del(file.id)' class='button delete'>削除</div>
                        </div>
                        <div class='buttons'>
                            <div class='button' v-on:click='add()'>ファイルを追加</div>
                            <div>
                                <div class='button public' v-if='isPublic' v-on:click='isPublic = !isPublic'>Public</div>
                                <div class='button' v-else='v-else' v-on:click='isPublic = !isPublic'>Secret</div>
                                <div class='button' v-on:click='send()'>作成する</div>
                            </div>
                        </div>
                    </div>
                    <div class='spinner center' v-if='isSended'></div>
                    <my-dialog ref='dialog'></my-dialog>
                </div>`,
    components: {
        'my-dialog': MyDialog
    },
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
        loadMode: function (id, event) {
            if (event.target.value.includes('.') && !event.target.value.endsWith('.')) {
                const extention = event.target.value.split('.').slice(-1)[0]
                if (extention in extentions) {
                    const script = document.createElement('script')
                    script.src = `${codeMirrorPath}/mode/${extentions[extention].mode}/${extentions[extention].mode}.js`
                    script.addEventListener('load', (event) => {
                        this.files[id].cm.setOption('mode', extentions[extention].mime)
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
                    if(file.name.length == 0) file.name = `gistfile${Object.keys(json.files).length + 1}.txt`
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
                        'Authorization': ` token ${this.token}`
                    })
                }).then((response) => {
                    if (response.status == 201) {
                        response.json()
                            .then((json) => {
                                this.$refs.dialog.alert('作成しました。', '作成したGistを表示します。', () => {
                                    this.$router.push(`/gists/${json.id}`)
                                })
                            })
                    } else throw new Error(`${response.status} ${response.statusText}`)
                })
                    .catch((err) => {
                        this.$refs.dialog.alert('エラーが発生しました。', err.toString(), () => {
                            this.isSended = false
                        })
                    })
            } else {
                this.$refs.dialog.alert('入力内容がありません。', '')
            }
        }
    }
}