#### LiveDemo
以下のアドレスで実際に利用できます。
https://flybird.jp:49650/

#### 必要な環境
- Go  
GOPATH等の設定が終わった状態から手順は書かせていただきます。
- ES Module対応ブラウザ

#### 手順
0. コンソールを開きます。(FlyBird-JPフォルダへ移動)
1. 必要なライブラリを導入します。
```
go get golang.org/x/oauth2 
go get github.com/gorilla/mux 
go get github.com/google/uuid
```
2. envフォルダをserverフォルダと同じ階層に作成し、その中に以下のファイルを作成します。  
```
mkdir env
cd env
```
- env/client_id.txt (UTF-8、UTF-8 BOMは未対応)  
Client IDを入力します。
- env/client_secret.txt (UTF-8、UTF-8 BOMは未対応)  
Client Secretを入力します。
- env/cert.pem と env/key.pem  
サーバー証明書を上記のファイル名で置きます。  
  
  ※参考  
  オレオレ証明書で良ければ以下のコマンドを実行してください。  
```
go run /usr/local/go/src/crypto/tls/generate_cert.go --host {ホスト名}
```
3. server/server.goの22行目にあるportの値を希望するHTTP2サーバーのポート番号へと変更してください。  
2.で作成したファイルもここで読み込まれるので確認してください。
4. server/server.goをビルドしてください。  
```
cd ../server
go build server.go
```
7. Github OAuth Appsの設定からリダイレクトURLを以下のように指定してください。  
```
https://ホスト名:指定したポート番号/callback_auth/github
```
8. 4.で出来た実行ファイルを実行します。  
```
./server
```

以上の手順で指定したポートをブラウザで開けば動作するはずです...。

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