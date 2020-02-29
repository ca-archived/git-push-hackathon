# 実行方法

```
// パッケージインストール
yarn install

// 環境変数用ファイル作成
cp .env.sample .env

// .envファイル編集
// この時，CLIENT_IDとCLIENT_SECRETSを書き換えてください
vim .env

// webpackサーバ起動
yarn dev

// 認証サーバ起動
node server/index.js
```

その後，`http://localhost:3000`にアクセスすると確認できます．
また，認証サーバは`http://localhost:8080`で起動します．

# 概要

現状 css などデザイン部分や UX の向上に関しては着手できていません．
プレイリスト一覧表示，動画一覧表示，プレイリストの追加，動画の追加に関して必要最低限の実装となっています．

# 技術ポイント

- React + Redux で store でのデータ一括管理
- できるかぎり Presentational Component と Container Component を意識
- 認証データをサーバサイドで取得し，Cookie としてブラウザ側に保持
