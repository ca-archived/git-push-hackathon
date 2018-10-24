#### LiveDemo
以下のアドレスで実際に利用できます。
https://flybird.jp:49650/

#### 必要な環境
- Go  
GOPATH等の設定が終わった状態から手順は書かせていただきます。
- ES Module対応ブラウザ  
最新のブラウザは一通り[対応していて](https://caniuse.com/#search=JavaScript%20modules%20via%20script%20tag)Gihubクライアントを使うであろう層的にも必要十分な要求だと考える...

#### 手順
0. コンソールを開きます。(FlyBird-JPフォルダへ移動)
1. 必要なライブラリを導入します。
```
go get golang.org/x/oauth2 
go get github.com/gorilla/mux 
go get github.com/google/uuid
```
2. envフォルダをserverフォルダと同じ階層に作成し、その中に下記のファイルを作成します。   
server/server.goがこれらのファイルを読み込むので何か問題があったら確認してください。
```
mkdir env
cd env
```
- env/client_id.txt (BOMには非対応)  
Client IDを入力します。
- env/client_secret.txt (BOMには非対応)  
Client Secretを入力します。
- env/cert.pem と env/key.pem  
サーバー証明書を上記のファイル名で置きます。  
  
  ※参考  
  オレオレ証明書で良ければ下記のコマンドを実行してください。  
```
go run "$(go env GOROOT)"/src/crypto/tls/generate_cert.go --host {ホスト名(localhost等)}
```
3. Github OAuth Appsの設定からリダイレクトURLを以下のように指定してください。  
```
https://ホスト名:指定したポート番号/callback_auth/github
```
4. server/sever.goを実行します。(`-port {ポート番号}`でポート番号を指定できます。デフォルトは49650)
```
cd ../server
go run server.go
```
または
```
cd ../server
go build server.go
./server
```


以上の手順を経てブラウザでアクセスすれば動作するはずです...。

#### 使用したライブラリ等
- [OAuth2 for Go](https://github.com/golang/oauth2)
- [gorilla/mux](https://github.com/gorilla/mux)
- [uuid](https://github.com/google/uuid)
- [CodeMirror](https://codemirror.net/)
- [Vue](https://jp.vuejs.org/)
- [Vue Router](https://router.vuejs.org/)
- [SpinKit](https://github.com/tobiasahlin/SpinKit)
- [GitHub Logo](https://github.com/logos)
- [Material icons](https://material.io/tools/icons/)