## git-push-hackathon-2018

![GitHub Gist Client](https://i.imgur.com/o4WkLzs.png)

GitHub API を用いた gist クライアント。

## 下準備

1. localhost のポート番号 3000 番と 8080 番を使用するので空けておく。 (重要)
1. GitHub OAuth の Authorization callback URL は `http://localhost:8080`に設定する。 (重要)
1. プロジェクトルート(`MaxfieldWalker/package.json`があるところ)に`.env`というファイル(ディレクトリではありません)を作り、以下の様式でクライアント ID とクライアントシークレットを記入しておく。 (重要)

```plaintext
GITHUB_CLIENT_ID = <クライアントID>
GITHUB_CLIENT_SECRET = <クライアントシークレット>
```

## ビルド & 実行方法

前節の下準備を済ませておく。

```bash
$ git clone <repo>
$ cd <repo>/MaxfieldWalker
$ npm install              # 依存ライブラリのインストール
$ node oauth/githubAuth.js # OAuthログインのために必要です。 (localhost:3000で起動していることを確認してください)
$ npm start                # もう1つターミナルを開いて実行してください。localhost:8080でアプリが起動します (localhost:8080で起動していることを確認してください)
```

## 作者

石田直人
maxfield.walker@gmail.com
