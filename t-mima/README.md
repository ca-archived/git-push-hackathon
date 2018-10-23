### 概要
gistを利用するにあたって、

・手軽にドキュメントやソースコードを共有できる点

・無料で secret の利用ができる点

の二点を生かそうと思い、gist API を利用したマークダウンエディタを作成いたしました。

gist client appを作成するにあたって、ソースコードは手元のエディタで編集することで間に合っていると考えられ、ソースコード以外のドキュメントを手軽に編集できる機能をもつことがgithub gistからの改良点になると考えました。

PCブラウザだけでなくモバイル端末からも、隙間時間に編集をすることを想定しております。

また、このクライアントアプリでは git を意識することなく、エンジニア以外のチームメンバーでも気軽に日報や議事録の共有、ダウンロードが出来ることも利点であると考えています。

### 構成

React, Flask

### 開発環境

node.js v8.12.0

npm v6.4.1

python v3.6.5

pip v18.0

確認済動作環境　chrome, edge (pc, android)

### ビルド方法

oauthのgithubからのcallbackは / でおねがいします。

1. oauth.py 9,10行目に client_id, client_secret を追加します。
2. src/login.jsのlogout()内のfetch先のhostをoauth.pyを動かすhost(localhost:5000)に変更し、
48行目のclient_id=にclient_idを挿入します。
3. src/app.js 59行目のfetch先のhostをoauth.pyを動かすhost(localhost:5000)に変更します。  
@ cd t-mima
4. pip install flask flask-cors requests
5. python oauth.py
6. npm install
7. npm start

本番では npm run buildしたものをgh-pagesに置きどこかで認証サーバを動かそうと思っておりました。

### 工夫・こだわり
+ ダウンロード
  + コードを実行できる環境は、ほぼPCに限定されているので、このアプリケーションは閲覧・編集機能のみとし、ソースコードはダウンロード出来るだけで充分だと考えました。
+ headerを使用していない点
  + スマホで文章入力する際に目に入る、記入する文章以外の情報は少ない方がよいと思います。
+ 配色
  + GitHubで採用されている配色にいたしました。
+ サーバ
  + GitHubのAPIを用いるのでgh-pagesで使えれば気持ちが良いと考えていましたが、OAuthを用いるのであればCORSでアクセストークンを取得できないので認証用にだけ、サーバを用意することにしました。保存の必要のあるデータはlocalStorageに保存するようにしました。oauth.ioやfirebaseなどを用いてもよかったと思っています。
+ 画面構成
  + 16 : 9 の画面にてなるべくテキストボックス、プレビューともにA判B判に採用されている 1 : 1.4 の比になるように、メニューから1 : 2 : 2 の比で画面を分割しました。
+ public gist
  + 一覧を眺めていても特に有益な情報が得られるとは思っていないので採用せず、スター一覧と特定ユーザの一覧を表示できるようにしました。
+ team
 + フォローフォロワーの関係が見える必要もないと感じたので一方的に相手のアカウント名を登録する形としました。