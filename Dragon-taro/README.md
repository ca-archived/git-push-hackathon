## 使い方

[oauth.io](https://qiita.com/RingCaptcha/items/7a63f2947df092131c83)というサービスを使って認証をとっています。

上記のサイトの設定に沿ってアプリを作成し、oauth.io でのアクセストークンを

```js:secret.js
export const ACCESS_TOKEN = "YOUR ACCESS TOKEN";
```

に追記します。

oauth.io での設定の際に scope の設定で gist の設定をするところがあるのでそれを忘れないようにしてください。（ここで僕は無駄に詰んでしまいました。）

これをしてから、

```shell
npm install
npm run build // or watch
npm run start
```

これでブラウザが立ち上がります。
