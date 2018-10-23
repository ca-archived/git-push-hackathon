### ビルドに関して

プロジェクト内のFinalProject/AppConfig.swiftを編集してください。

``` AppConfig.swift
struct AppConfig {
    static let clientId = ""
    static let clientSecret = ""
    static let callbackUrl = ""
}
```

ライブラリの管理にはCocoaPodsを使用しています。
```
pod install
```
をお願いします。

### 成果物について

お題の指定要件
- GitHubのOAuthを用いたログインができる
- /gists APIを用いた機能がある
   - gistの一覧表示ができる
   - gistを投稿できる

を満たしおまけで
- 投稿したgistを削除できる
- フォロワーの一覧およびgistを閲覧できる

ものとなっております。


### アピールポイント等

- 設計
  - 設計に関してはRxSwift+MVVMとReactorKitを使ったものが混在しております。
  - DIを意識しプロトコルを用いて疎結合になるようにしました。

- セキュリティー
  - 簡易的なもののためaccessTokenはUserDefaultに保存します。 

- その他工夫したところ
  - 成果物が実際にアプリとして使えるかという点を個人的に意識しました。
  - UIはオレンジをテーマに作成してみました。
  - gistの閲覧方法も工夫してみようと思い、gistのファイルが複数ある場合はTabMenuのようにスワイプで閲覧できるようにしてみたのですがイマイチでした・・・
  -  ソースの書き方を一部変えてたりオリジナルの部分を組み込んだりして開発を楽しみました。
  
### 開発環境
諸事情により最新版ではございません・・・
- swift 4.1 
- xcode 9.4.1


