#### LiveDemo
以下のアドレスで実際に利用できます。(サーバー証明書取得の手順は異なります。)  
https://flybird.jp:49651/

#### 必要な環境
- Go  
GOPATH等の設定が終わった状態から手順を書かせていただきます。

#### 手順
1. 必要なライブラリを導入します。  
`go get golang.org/x/oauth2`  
`go get github.com/gorilla/mux`  
`go get github.com/google/uuid`
2. environmentフォルダに以下のファイルを作成します。
- client_id.txt (UTF-8 BOMは未対応)  
Client IDを入力します。
- client_secret.txt (UTF-8 BOMは未対応)  
Client Secretを入力します。
- cert.pem  
- key.pem  
サーバー証明書を上記のファイル名で置きます。  
(参考)　オレオレ証明書で良ければ以下のコマンドをenvironmentフォルダで実行してください。  
`go run /usr/local/go/src/crypto/tls/generate_cert.go --host {ホスト名}`
3. server/server.goの22行目にあるportの値を希望するHTTP2サーバーのポート番号へと変更してください。  
2.で作成したファイルもここで読み込まれるので確認してください。
4. server/server.goをビルドしてください。  
`go build server.go`  
5. [CodeMirror](https://codemirror.net/codemirror.zip)をダウンロードし、client/codemirror/(srcやmode)となるよう解凍します。  
6. [Githubのロゴ](https://github-media-downloads.s3.amazonaws.com/GitHub-Mark.zip)をダウンロードして、その中のGitHub-Mark-120px-plus.pngをclient/images以下に解凍してください。  
7. 4.で出来た実行ファイルを実行します。  
`./server`  

以上の手順で指定したポートをブラウザで開けば動作するはずです...

#### 使用したライブラリ等
- [OAuth2 for Go](https://github.com/golang/oauth2)
- [gorilla/mux](https://github.com/gorilla/mux)
- [uuid](https://github.com/google/uuid)
- [CodeMirror](https://codemirror.net/)
- [Vue](https://jp.vuejs.org/)
- [Vue Router](https://router.vuejs.org/)
- [SpinKit](https://github.com/tobiasahlin/SpinKit)