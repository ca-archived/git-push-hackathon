## git-push-hackathon-2018

GitHub API を用いた gist クライアント。

## 下準備

プロジェクトルート(`package.json`があるところ)に`.env`というファイルを作り、以下の様式でクライアント ID とクライアントシークレットを記入しておく。

```plaintext
GITHUB_CLIENT_ID = <クライアントID>
GITHUB_CLIENT_SECRET = <クライアントシークレット>
```

## ビルド & 実行方法

前節の下準備を済ませておく。

```bash
$ git clone <repo>
$ cd <repo>
$ npm install              # 依存ライブラリのインストール
$ node oauth/githubAuth.js # OAuthログインのために必要
$ npm start                # localhost:8080でアプリが起動します
```

## 作者

石田直人
maxfield.walker@gmail.com
