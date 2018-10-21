<!--
Thank you for your pull request! Before you submit, make sure to review the requirements below.

プルリクエストありがとうございます！下記の要件を確認してください。
-->

### Appeal Point / アピールポイント

<!--
Write down things you have devised or put effort on.

工夫した点・こだわったポイントがあればこちらでアピールしてください。
-->

#### システム構成
基本的には全ての処理をUXの点からSPAとしてクライアントサイドで行いますが、CORS未対応の問題やAPI Keyの管理などの点から、別に認証用サーバーを用意しました。クライアントサイドのルーターにはVue Routerを使用しています。

#### コンポーネント指向
見通しの良い設計を目指し、以下の6つのコンポーネントを作成しました。最初はCustom Elementsで作成していましたが、ブラウザ互換性やルーターとの相性から最終的にはVueのコンポーネントとして作成しています。
- login-github  
非ログイン時はOAuth認証をサーバーにリクエストするためのボタン、ログイン時はユーザー名を表示し、イベント一覧を表示するためのボタンとして機能します。
- github-event  
指定されたURLからイベントの情報を取得し結果を表示します。
- gist-list  
GistのリストをAPIから取得し、個別のGistを表すJSONをBlob化、そのBlobにアクセスできるオブジェクトURLを属性に含んだgist-itemを並べます。これによりgist-itemはgist-listに依存しません。
- gist-item  
指定されたURLまたはIDからGistの情報をリクエストし結果を表示します。detail属性が指定されていると、埋め込み用Scriptによる内容の確認や、ログイン時にはGistにスターをつけたりフォーク削除が行えます。
- gist-editor  
Gistを作成するためのエディターを提供します。Githubも利用しているライブラリー、CodeMirrorを利用しています。入力されたファイル名の拡張子を判別し、シンタックスハイライト用のファイルを自動的にダウンロードできるようにしました。
- my-dialog  
処理をブロックするwindow.alert、window.confirm、window.promptの代わりとして作成しました。コールバック関数で結果を受け取ることができます。

#### stateパラメーターの利用
OAuth認証時、stateパラメーターを利用してCSRF対策を行っています。

#### ログインしてないユーザーにもPublicなGistの表示機能は提供
アクセストークンがなくても一部のリクエストは受け付けてもらえるようなのでログインしてないユーザーでも利用できるようにしています。ただいきなりログインしろ、と言うのではなく機能の一部を使って貰った状態でログインを促した方がユーザ的にも納得感があると考えました。

#### イベントの表示
前回のお題がイベントの一覧表示だったようなので同機能を実装しました。

#### 無限スクロール
Gistの一覧表示にはIntersection Observer APIを利用した無限スクロール機能が実装されています。APIが利用できない場合は読み込み用ボタンを別に表示し、配慮を行っています。

#### デザイン面
PCで見ても、スマートフォンで見ても違和感のないレスポンシブな、Githubユーザーに馴染みやすいデザインを心がけました。

#### その他UXへの細かい配慮
ページ間の遷移やgist-item、github-eventの表示、その他処理待ちが発生する場所ではCSSによるトランジション演出やアニメーションでストレスの少ない操作を目指しています。

#### HTTP2サーバーの利用
このハッカソンは主催者側の環境で評価してもらう形ということもあり出来るだけ構成を単純にしたかったので、webpack等は使っていませんが同時リクエスト数に制限のないHTTP2サーバーを利用して少しでも早くコンポーネント群などを読み込めるようにしました。

---

環境構築の手順や使用したライブラリ等の情報はREADME.mdに記載しました。


### Checklist / チェックリスト

<!--
Read the checklist below and mark appropriate one(s) as checked.

確認できたら[ ]を[x]に変更ください。
-->

- [ ] Your project fulfills the minimum requirements. / お題の最低要件は満たしている
- [ ] We can build your project. / ビルド方法が記載されている
- [ ] Your poject don't contain (Or we consider it contains) copy-pasted source code. / コピペだと思われるソースコードの仕様をしていない
- [ ] Devices and commitment are described. / 工夫、こだわりが記載されている

<!--
You can submit this pull request even if it is incomplete, but please note that it will not be eligible for evaluation.

未完成のソースコードも提出可能です。未完成のソースコードは、表彰の対象にはなりませんのでご注意ください。
-->
