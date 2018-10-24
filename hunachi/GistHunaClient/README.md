# GistHunaClient

[git-push-hackathon](https://github.com/CyberAgent/git-push-hackathon) val2の為のアプリです．

## 実行方法

GithubのDeveloperSettingで新しいOAuth Appを登録して下さい．
Authorization callback URL には`gisthuna://oauth`と設定して下さい．
登録時に得られた clientId と clientSecret をproject rootの`local.properties`に以下のように追加して下さい．
```
CLIENT_ID=hogehoge
CLIENT_SECRET=hogehoge
```

## アプリ概要

| 認証前Home | 認証の仕方 | 認証後Home | Gist作成画面 |
|:-----------:|:------------:|:------------:|:----:|
| <img src="https://user-images.githubusercontent.com/16878520/47428189-7a01b580-d7cd-11e8-9be9-7fde24cb769c.png" width="150"> | <img src="https://user-images.githubusercontent.com/16878520/47428187-7706c500-d7cd-11e8-9a90-c84fff962640.png" width="150"> | <img src="https://user-images.githubusercontent.com/16878520/47428197-7f5f0000-d7cd-11e8-9009-c0d209c43450.png" width="150"> | <img src="https://user-images.githubusercontent.com/16878520/47428195-7cfca600-d7cd-11e8-9b68-10683d3eaea5.png" width="150"> |
| 初めてアプリを開いた時の状態です.最初は，世界中の人のpublicなgistsが表示されています． | 認証は，App Bar右上に表示されているアイコンをクリックすると，「OAuth認証をする！」というアイテムが表示されるのでそれをタップして下さい．認証画面に移動します． | 認証後は，自分のGists一覧が表示されます．また，右下のfabを押すとGist作成画面に遷移します． | Gistを投稿する事ができます．➕のついたボタンをタップする事で添付できるFile数を増やせます．内容の入力が終わり，投稿する際は，下の黄色のfabを押して下さい．|


## 使用したライブラリ
- Paging Library
- Room
- ViewModel
- LiveData
- Retrofit2
- moshi
- okhttp3
- koin
- android-ktx
- databinding
- AVLoadingIndicatorView
- Kotlin Coroutine
- Kotlin Coroutine Adapter

## こだわり

### 設計に関して

- ##### module分割

moduleの構成は以下の画像の通りです．

![image](https://user-images.githubusercontent.com/16878520/47433882-48441b00-d7dc-11e8-9791-999718ee8bfb.png)

コンパイル時間の短縮，app bundle対応に向けた設計を行いました．

これにより，OAuth認証をしなくてもアプリを使用したい方は，oauth moduleとoauth moduleのみに依存する全てのmoduleをinstallしなくてすみ，アプリのサイズが小さくなります．

（実際のアプリではこんなことする物はないかもしれませんが！）

※ただし今回は私の時間の余裕的に実装が難しかった為．app bundle部分の細かい実装は行っていません．

oauthと同じような形で，moduleを追加して行けばもっとapp bundleに対応したアプリになると思います．

- ##### Paging Library ✖ Room (✖ Github Api )

Paging Library ✖ Room (✖ Github Api )を使うことで，簡潔な実装のスムーズにスクロールの出来るListを実現しています．

また，ネット環境がない状態でもCacheされたデータが表示されるのでUXの向上にも繋がっています．

以下の画像のような動きをします．

![image](https://user-images.githubusercontent.com/16878520/47431375-a241e200-d7d6-11e8-9572-319b6823bdeb.png)

image from : https://www.captechconsulting.com/blogs/an-overview-of-android-jetpack

- ##### Flux

Presentation Layer では．fluxを採用することで読みやすい簡潔なコードを実現しました．

FragmentやActivity間のActionの通知も，楽に行えます．

- ##### Infra & UseCase周り

Layered Architecture（もどき）の実装 & internal（module分割の恩恵）を使うことでカプセル化を行うことができました．
それにより可読性が高く，安全に扱えるコードが実現できました．

基本的なデータ伝達の流れは以下の通りです．

Github Api -> Client -> Repository -> UseCase -> UI

※Paging Libraryを使用した部分をのぞきます．

---
### 非同期処理
- Rxを一切使ってません！
- Kotlin Coroutine と LiveData のみを使ってスマートな非同期処理コードを実現しています．
- fluxを実装する際も．EventBusを使わず，Kotlin CoroutineのChannelを活用し実装しています．

---
### かわいいgopher君

このアプリにはアイコンを始めとし，かわいいgopher君が何匹か登場します．（gopherのオリジナルデザインは，Renee Frenchさんによるものです．）

<img src="https://user-images.githubusercontent.com/16878520/47426883-94399480-d7c9-11e8-8d23-45fb734e6e66.png" width="200">
<img src="https://user-images.githubusercontent.com/16878520/47427035-204bbc00-d7ca-11e8-9570-c0f29d1476e3.png" width="200">
<img src="https://user-images.githubusercontent.com/16878520/47427040-2477d980-d7ca-11e8-8a5f-c06606bd3eef.png" width="200">


## 時間があったらしたかった事

- gist詳細画面の実装．
- 一定以上更新されてないgistのcacheを消す処理を入れる．
- dependences.gradleでversion管理．
