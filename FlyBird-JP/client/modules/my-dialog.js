export default {
    template: `<div class='my-dialog' v-bind:class='{visible:mode != "close"}'>
                    <div class='dialog'>
                        <h2>{{ title }}</h2>
                        <p v-bind:class='{"min":mode == "prompt"}'>{{ messeage }}</p>
                        <input v-if='["prompt"].includes(mode)' type='text' v-model='input' v-on:keyup.enter='enter()'></input>
                        <div class='buttons'>
                            <button class='button negative' v-on:click='negative()' v-if='mode != "alert"'>キャンセル</button>
                            <button class='button positive' v-on:click='positive()'>OK</button>
                        </div>
                    </div>
                </div>`,
    data: function () {
        return {
            'title': 'タイトル',
            'input': '',
            'messeage': 'メッセージ',
            'mode': 'close'
        }
    },
    methods: {
        alert: function (title, messeage, callback) {
            this.title = title
            this.messeage = messeage
            this.callback = callback
            this.mode = 'alert'
        },
        confirm: function (title, messeage, callback) {
            this.title = title
            this.messeage = messeage
            this.callback = callback
            this.mode = 'confirm'
        },
        prompt: function (title, messeage, callback) {
            this.title = title
            this.messeage = messeage
            this.callback = callback
            this.mode = 'prompt'
        },
        negative: function () {
            if (this.callback != null) {
                switch (this.mode) {
                    case 'alert':
                        this.callback()
                        break;
                    case 'confirm':
                        this.callback(false)
                        break;
                    case 'prompt':
                        this.callback(null)
                        break;
                }
            }
            this.reset()
        },
        positive: function () {
            if (this.callback != null) {
                switch (this.mode) {
                    case 'alert':
                        this.callback()
                        break;
                    case 'confirm':
                        this.callback(true)
                        break;
                    case 'prompt':
                        if (this.input.length > 0) this.callback(this.input)
                        else this.callback(null)
                        break;
                }
            }
            this.reset()
        },
        reset: function () {
            this.title = 'タイトル'
            this.messeage = 'メッセージ'
            this.input = ''
            this.positiveButton = ''
            this.negativeButton = ''
            this.mode = 'close'
            this.callback = null
        }
    }
}