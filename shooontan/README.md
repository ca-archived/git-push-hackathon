# git-push-hackathon :computer: vol.2

git submodule で成果物を提出します。submodule を更新してください。以後、submodule 側のリポジトリについて説明します。

## 開発環境

- docker-compose :whale:
  - 1.22.0
- Node.js
  - v10.11.0
- yarn :cat2:
  - 1.10.1

## 動作確認

### 事前準備

Google の謎の技術「Firebase Authentication」をユーザー認証に使用しています。そのため 以下で使用するトークンを事前に [https://console.firebase.google.com/](https://console.firebase.google.com/) で取得する必要があります。

### 環境変数

1. `/app` 下にある `.env.sample` を `.env` にコピーします。

```bash
$ cp .env.sample .env
```

2. 取得しておいた Firebase トークンを `.env` 環境変数にセットします。

## 実行

```bash
# ホスト側
$ docker-compose up -d
$ docker-compose exec app bash


# コンテナ側

# node_module が入ってない場合
$ yarn

# 開発 (ポート3000)
$ yarn dev

# ビルド (ポート3000)
$ yarn export && yarn server
```

成果物は静的サイトとしてビルド、デプロイすることを想定しています。

デモページ： [https://portalgist.netlify.com](https://portalgist.netlify.com)

Firebase やその他の部分で IndexedDB・localStorage を使用しています。最新版 Chrome だと、通常モード・シークレットモードどちらも問題なく動作します。スマホ対応はできていません。

## ページ構成

### トップページ [/]

パブリックな gist を表示しています。

### ユーザーページ [/user/?name=username]

特定ユーザーの公開されいている gist を一覧表示しています。

また、ログインした状態で自分のページを見ている場合は、非公開の gist も表示されるようになっています。

動的に URL を生成することができないため、ユーザーの判別にクエリパラメーターを使用しています。

### gist ページ　[/gists/?id=gistid]

対象となる gist を表示しています。

### 編集ページ [/gists/edit/?id=gistid]

対象となる gist の編集・更新が行えます。削除はできません。間に合いませんでした。

## 設計

主なフレームワーク・ライブラリに Next.js を使用しました。また、精神的安定を得るために TypeScript でコードを書いています。データ管理には redux, ミドルウェアに redux-thunk を使用しました。一部をのぞいた大部分のスタイル調整に styled-components を使用しています。

### state 管理

王道の redux です。Flux Standard Action に従いアクションをつくっています。TypeScript で愚直にアクションなどを書いていくとコード量が多くなるため、typescript-fsa, typescript-fsa-reducers の謎の力を借りています。これにより、非同期処理で使われがちな `start`, `success`, `failed` に該当するアクションを一度に作ることができるようになりました。

### コンポーネント

自己流に解釈した Atomic Design でコンポーネントを作っています。今回は以下のようにルールを決めました。

- atoms
  - props のみに依存する。他のコンポーネントをインポートしていない
  - styled-components や Link など一部例外あり
- molecules
  - props もしくは atom のみに依存する
  - 一部例外あり
- organisms
  - atom や molecule をインポートしている
  - store と connect してもいい
- templates
  - レイアウト
- pages
  - URL に一対一対応
  - Next.js が提供してくれる ルーティングディレクトリをそのまま使用

### UI・UX

ページのデザインは某ホスティングサービスを参考にしています。SPA でもあるので少しだけローディングアニメーションを導入しています。コンテンツ部分が薄色の帯になってビロビロビロ〜ってなるアニメーションをはじめて実装してみました。n = 1 ではありますが、実装前と比べるとそれなりの待ち時間軽減効果がでていると思っています。

GitHub API アクセスの非同期処理は、Next.js から提供される `getInitialProps` もしくは `componentDidMount` 内で行なっています。分けている分ややこしくなっているため、改善点のひとつではあります。

### エディター

編集ページで表示されるエディターについて。機能性を重視した結果、Monaco Editor を導入しました。ただ、数日で使えるようになるはずもなく、結果としてビルド時間が伸びました。クライアント側では Next.js 経由の dynamic import で読み込んでいます。

## その他

本家 [https://gist.github.com/](https://gist.github.com/) の劣化版になっています。差別化としてオフラインで動作する機能があればな〜とか考えていましたが、考えただけでした。

評価をよろしくお願いします :pray:
