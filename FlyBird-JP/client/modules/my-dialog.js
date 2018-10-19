export default {
    template: `<div class='my-dialog' v-bind:class='{visible:mode != "close"}'>
                    <div class='dialog'>
                        <h2>{{ title }}</h2>
                        <p v-bind:class='{min:["prompt"].includes(mode)}'>{{ messeage }}</p>
                        <input v-if='["prompt"].includes(mode)' type='text' v-model='input' v-on:keyup.enter='enter()'></input>
                        <div class='buttons'>
                            <div class='button positive' v-if='["confirm"].includes(mode)' v-on:click='yes()'>YES</div>
                            <div class='button negative' v-if='["confirm", "prompt"].includes(mode)' v-on:click='cancel()'>CANCEL</div>
                            <div class='button positive' v-if='mode == "prompt"' v-on:click='enter()'>ENTER</div>
                            <div class='button positive' v-if='mode == "alert"' v-on:click='close()'>OK</div>
                        </div>
                    </div>
                </div>`,
    data : function() {
        return {
            'title': 'タイトル',
            'input' : '',
            'messeage' : 'メッセージ',
            'mode': 'close'
        }
    },
    methods: {
        alert : function(title, messeage) {
            this.title = title
            this.messeage = messeage
            this.mode = 'alert'
        },
        confirm : function(title, messeage, callback) {
            this.title = title
            this.messeage = messeage
            this.callback = callback
            this.mode = 'confirm'
        },
        prompt : function(title, messeage, callback) {
            this.title = title
            this.messeage = messeage
            this.callback = callback
            this.mode = 'prompt'
        },
        enter : function() {
            this.close()
            if(this.callback != null) {
                this.callback(this.input)
                this.callback = null
            }
            this.reset()
        },
        yes :  function (){
            this.close()
            if(this.callback != null) {
                this.callback(true)
                this.callback = null
            }
            this.reset()
        },
        cancel :  function (){
            this.close()
            if(this.callback != null) {
                this.callback(false)
                this.callback = null
            }
            this.reset()
        },
        close : function (){
            this.mode = 'close'
        },
        reset: function(){
            this.title = 'タイトル'
            this.messeage = 'メッセージ'
            this.input = ''
        }
    }
}