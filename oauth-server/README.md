# OAuthServre

このプロジェクトは Githubb-AppのためのGithub OAuth認証を行うためのサーバーをことにあります。
サーバはC# .NET frameworkで作られています。そのためvisual Stdioで.slnファイルを開き実行する必要があります。

## 実行までの流れ

プロジェクトの.slnファイルをVisual Stdioで開きます。

###必要なパッケージの用意

このプロジェクトではC#でJSONを扱う場面が出てくるので、JSONを扱うパッケージ(Json.NET)をNuGetを使って入れる必要があります。
ツール>NuGetパッケージマネージャー>ソリューションのNuGetパッケージ管理　を実行し
オンラインタブから、「Json.net」を検索し、Json.NET(識別がNewtonsoft.Jsonにのものです)をインストールします

###OAuthServreの実行

サ―バーの実行は、ビルドして作成されたアプリケーションを実行する方法と、デバッグから実行する方法があります。
両者で大差はないのでどちらからでも構いません。
