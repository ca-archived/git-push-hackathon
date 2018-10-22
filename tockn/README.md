# Git Push Hackathon
tocknのプロジェクト

# 概要

## 機能
- GitHubのOAuthを用いたログイン
- /gists APIを用いた機能がある
  - gistの一覧表示
     - Public Gist一覧
     - 任意のUserのGist一覧
  - gistを投稿できる
  - gistのstar, unstarができる
- userの検索ができる
- ログアウト  
~~gistの編集削除（未完成）~~

## 特徴
- Descriptionをメインにした一覧表示
- スマートフォンでも閲覧、投稿がしやすいデザイン
- キーボードの入力イベント発火型のユーザー検索機能（インクリメンタルサーチっぽく）

# 環境構築

## フロント

フロントエンドのビルドにはvue-cli2の環境が必要です。  
また、各パッケージをインストールするために`view/`内で`npm install`してください。  
その後、`view/config/dev.env.js`内の`OAUTH_ENDPOINT`へ後述するOAuthサーバーのURLを設定してください。(`'`と`"`に気をつけてください...)  
例：
`API_ENDPOINT: '"http://localhost:3000'"`
  
## OAuth認証サーバー

OAuth認証のために、Go製のサーバーを立てる必要があります。Go 1.10以上を推奨します。  
環境変数の設定をします。  
認証サーバーのエンドポイントを`$API_ENDPOINT`へ書きます。  
認証サーバーは3000番ポートでサーブします。  
`npm run dev`でサーブする時のURLも`$REDIRECT_URL`へ設定してください。デフォルトは8080番です。  
例：  
`export API_ENDPOINT=http://localhost:3000`  
`export REDIRECT_URL=http://localhost:8080`  
  
GitHub ApplicationのCLIENT IDを`$CLIENT_ID`へ、CLIENT SECRETを`$CLIENT_SECRET`へ設定してください。  
SESSION SECRETを設定する必要があるので、任意の文字を`$SESSION_SECRET`へ設定してください。
  
次に、Goのpackageの依存関係を解決するために、`tockn/`で`make deps`してください。(depを入れた後、`dep ensure`で依存解決をします。)

## さいご

以上が完了したら、`tockn/`で`make dev`してください。または`go run main.go`と`npm run dev --prefix view`してください。


# 設計

## アーキテクチャ
Vue.js + Vuexを用い、MVVMのアーキテクチャを採用しています。

![Imgur](https://i.imgur.com/thZYCS5.png)

## ディレクトリ構成

```
view/
  └ src/
    ├ components/
    │   ├ globals/
    │   ├ pages/
    │   └ parts/
    │
    ├ store/
    │   └ modules/
    │       └ auth/
    │       └ gists/
    │       └ users/
    │
    ├ router/
    ├ assets/
```

## components (View層 + ViewModel層)

ここにはVueコンポーネントが入ります。

### globals

ルートコンポーネント(App.vue)から直接呼び出され、常に描画されるコンポーネント群。
今回はヘッダーがここにあたる。Vuexを介してデータのやり取りを行う。

### pages

routerによって呼び出されるコンポーネント群。`globals`同様Vuexでデータのやり取りを行う。

### parts

`globals` `pages`から呼び出される子コンポーネント群。Vuexと直接やり取りせず、親コンポーネントとのデータのやり取りのみを行う。

## store (Model層)

Vuex関連。modulesで切り分けもした。GitHub APIとの通信はこの層の責務。components側でstateをcomputedで監視することによってMVVMを実装しています。また、`vuex-persistedstate`を使用してstateであるアクセストークンを永続化しています。これによってページをリロードしても再認可する必要がありません。

# 画面

![Imgur](https://i.imgur.com/C3k9BmT.png)
  
## Search  
![Imgur](https://i.imgur.com/EarKYt3.gif)
