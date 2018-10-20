# github-appの実行(アプリケーションのクライアントサイド)

github-appはAngular CLI(version 6以降)を使用して作成されています。
そのため実行環境でAngularCLIを入れてください。

また、nodeモジュールの@sgbj/angular-gist, postscribe, ngx-cookie-service を使用しています。
またこのモジュールは標準でインストールされないはずなので、angularのプロジェクトディレクトリ(Kusunoki-19/github-app/github-app)でnpm installコマンドを使って上記のモジュールをインストールしてください。

それが整った状態で、angularのプロジェクトディレクトリでng serve　--openコマンドを実行することで、angular開発のサーバーが起動し、ブラウザが自動で立ち上がります。
何もプログラムを編集していない状態であれば、angularの標準である http://localhost:4200/ で再発サーバーが実行されます。

またOAuth認証を行うときは、あらかじめoauth-serverを実行しておいてください。

# oauth-serverの実行(アプリケーションのサーバーサイド)

oauth-serverはgithubb-app(アプリケーションのクライアントサイド)のためのGithub OAuth認証を行うためのサーバーです。
サーバはC# .NET frameworkで作られています。そのためVisual Stdioで.slnファイルを開き、実行する必要があります。

## .NET frameworkの必要なフレームワークの用意

このプロジェクトではC#でJSONを扱う場面が出てくるので、JSONを扱うフレームワーク(Json.NET)をNuGetを使って追加する必要があります。
ツール>NuGetパッケージマネージャー>ソリューションのNuGetパッケージ管理　を実行し、オンラインタブから、「Json.net」を検索し、Json.NET(識別がNewtonsoft.Jsonにのもの)をインストールします。

インストール終わったら、ソリューションエクスプローラー>「参照設定」で右クリック>「参照の追加」を選択します。
選択すると、参照マネージャが表れるので、アセンブリ>フレームワークを選択した状態で、Json.NETと検索し、Json.NETをチェックし、「OK」をクリックします。

## serverの実行

サ―バーの実行は、ビルドして作成されたアプリケーションを実行する方法と、デバッグから実行する方法があります。
両者で大差はないのでどちらからでも構いません。


# 実行できない場合

環境によっては、アプリケーションが実行できない場合があるので、その場合の原因と対処をまとめました　

## OAuthServreで使用しているポート番号が他のアプリケーションで占有されている

プログラムを触っていなければOAuthServerはhttp://localhost:4201/で実行されます。
そのため、port 4201でほかのアプリケーションが実行されている場合は、サーバーを実行することができません。

その場合は、OAuthServerのProgram.csにあるGlobalValsクラスのSERVER_ORIGIN変数の中身を編集し、使用していないポート番号に変更します。
それに伴い、Github-appのプログラムに記載されているサーバーのアドレスも変更する必要があります。
変更は、Kusunoki-19/github-app/github-app/src/app/api.service.ts にある ApiServiceクラス,OAuthServerOriginメンバを変更します。

## 必要な環境がそろっていない

このアプリケーションでは、AngularCLIを使用したクライアントサイド,C# .NET frameworkを使用したサーバーサイドで構成されています。
その中で、上記のモジュール(@sgbj/angular-gist, postscribe, ngx-cookie-service)と、フレームワーク(Json.NET)は手動で環境に整える必要があります。
モジュールはnpm listで、フレームワークは、プロジェクトのソリューションエクスプローラーの参照設定から参照を確認してください。