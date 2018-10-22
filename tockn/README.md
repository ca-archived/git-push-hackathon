## Git Push Hackathon
tockn (佐藤琢斗)

## 概要

### 機能
- GitHubのOAuthを用いたログイン
- /gists APIを用いた機能がある
  - gistの一覧表示
  - gistを投稿できる
  - gistのstar, unstarができる +1
- userの検索ができる

## 環境構築

## 設計

### アーキテクチャ
Vue.js + Vuexを用い、MVVMのアーキテクチャを採用しています。

![Architecture](https://i.imgur.com/53usCll.png)

### ディレクトリ構成

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

### components (View層 + ViewModel層)

ここにはVueコンポーネントが入ります。

#### globals

ルートコンポーネント(App.vue)から直接呼び出され、常に描画されるコンポーネント群。
今回はヘッダーがここにあたる。Vuexを介してデータのやり取りを行う。

#### pages

routerによって呼び出されるコンポーネント群。`globals`同様Vuexでデータのやり取りを行う。

#### parts

`globals` `pages`から呼び出される子コンポーネント群。Vuexと直接やり取りせず、親コンポーネントとのデータのやり取りのみを行う。

### store (Model層)

Vuex関連。modulesで切り分けたり。また、`vuex-persistedstate`を使用してstateの永続化しています。（ページを読み込むたびにアクセストークンを取得する必要がなくなったり）

