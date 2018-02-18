# ビルド方法
## 前提
- ANDROID_HOMEにAndroid SDKのパスが設定されていること。もしくはgit-push-hackathon/YokoMasa/以下にlocal.properties
という名前のファイルを作成し、以下の文を記入してください
```
sdk.dir=(Android SDKのパス)
```
- Java 8 SDKがインストールされていること。Java 9 ではビルドできませんでした。
- 必要なAndroid sdkツールがインストールされていること。もしされていない場合以下を実行してください。
```
sdkmanager "platforms;android-26" "build-tools;26.0.2"
```
## ビルド手順
1. リポジトリをクローンしてください。
```
git clone https://github.com/YokoMasa/git-push-hackathon.git
```


2. 文字リソースを追加してください。
git-push-hackathon/YokoMasa/app/src/main/res/values/以下にconfig.xmlという名前のファイルを追加し、以下の文を記入してください。
```xml
<?xml version="1.0" encoding="UTF-8"?>
<resources>
    <string name="client_id">お持ちのclient_id</string>
    <string name="client_secret">お持ちのclient_secret</string>
    <string name="oauth_redirect_scheme">設定したコールバックURIのスキーム</string>
    <string name="oauth_redirect_host">設定したコールバックURIのホスト</string>
</resources>
```
例）設定したURIが`example://login`の場合以下のようなリソースになります。 
```xml
<string name="oauth_redirect_scheme">example</string>
<string name="oauth_redirect_host">login</string>
```


3. 以下のコマンドを実行してください。
```
cd git-push-hackathon/YokoMasa
chmod 755 gradlew
./gradlew assembleDebug
```

ビルドが成功するとgit-push-hackathon/YokoMasa/app/build/outputs/apk/debug以下にapp-debug.apkが生成されます。これをAndroid端末にインストールしてください。
