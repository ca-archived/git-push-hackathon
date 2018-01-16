# Git Push Hackathon

touyou

## プロジェクト構成

- GitMe ... iOS版・メイン
- GitMeAndroid ... 同様の設計でのAndroid移植

## 使用方法

iOS版は最初にフォルダ内で

```
$ make
```

をしてCarthageのライブラリをインストールしてください。

その後プロジェクトのSupportFilesの中に`ClientKey.plist`を作成し、Root直下に`client_id`をKeyにClient IDのStringと、`client_secret`をKeyにClient SecretのStringを追加してください。
