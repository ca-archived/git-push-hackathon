## 開発環境
- Xcode 9.4.1
- Swift 4.1.2
- Pods 1.5.2

## 使用ライブラリ
- Alamofire
- Kingfisher
- RxCocoa
- RxSwift

## 導入方法
1. https://github.com/settings/applications/new にアクセスし、Authorization callback URLに`hackathon://`と記入して登録してください。
2. `Client.plist`の`client_id`と`client_secret`に、ご自身のclient_idとclient_secretを記入してください。
![Client.plist](https://github.com/tomoyamatsuyama/git-push-hackathon/blob/master/tomoyamatsuyama/vol2/Client.png)
3. pod installをしてください。

## アピールポイント
vol1も参加したのですが、その時とは設計をがらりと変更しました。
今回、実装できた機能は最低限ですが、今後拡張出来るように工夫しました。

### 設計
設計は、clean architectureとVIPERを混ぜました。
基本、layer間はRxSwifrtを用いています。
protocolで依存関係を解消しています。
テストも実装したかったのですが、出来なかったです。

### 所感
最適な設計選定ではなかったと思いました。（機能や、実装日数を考慮すると）

また、不具合も自分で把握しています。（OAuthログインと、gistの一覧取得、gistの投稿は出来ます）

