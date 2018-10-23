# Gistapp

### 開発環境

- macOS High Sierra '10.13.6'
- Laravel Homestead '7.18.0'
- Laravel Framework '5.5.44'

### 環境構築

環境はvagrantとHomesteadを用いています。vagrantは[公式](https://www.vagrantup.com/)からダウンロードお願いします。

Homesteadのダウンロード
```shell
$ vagrant box add laravel/homestead
```
プロバイダの指定はvirtualboxでお願いします。  
続いてHomesteadのboxを使用する際の設定がまとめられたファイルをホームディレクトリにダウンロードします。
```shell
$ cd ~
$ git clone https://github.com/laravel/homestead.git Homestead
```

ダウンロード後、Homesteadの初期化を行います。
```shell
$ cd ~/Homestead
$ bash init.sh
```
続いてHomestead.yamlを以下のように編集します。
```Homestead.yaml
ip: "192.168.10.10"
memory: 2048
cpus: 1
provider: virtualbox

authorize: ~/.ssh/id_rsa.pub

keys:
    - ~/.ssh/id_rsa

folders:
    - map: ~/code
      to: /home/vagrant/code/

sites:
    - map: gist.test
      to: /home/vagrant/code/git-push-hackathon/MyPoZi/gistapp/public
databases:
    - gist

```

次に、共有ディレクトリの設定と、MyPoZiというディレクトリが入ったgit-push-hackathonリポジトリをcloneします。
```shell
$ cd ~
$ mkdir code
$ cd code
$ git clone https://github.com/CyberAgent/git-push-hackathon
```
次に/etc/hostsの設定を行います
```hosts
192.168.10.10   gist.test
```
を追加してください。

続いてVagrantを起動し、ログインします。
```shell
$ cd ~/Homestead
$ vagrant up
$ vagrant ssh
```
sshでログインしたままLaravelプロジェクトを確認します。
```shell
$ cd ~/code/
```
git-push-hackathonがなければvirtualboxの中でgit cloneしてください。

続いてmysqlにrootで入り、ユーザーを作り、権限を与えます。
```shell
$ mysql -u root
```
```mysql
mysql> create user gist identified by 'secret';
mysql> grant all on gist.* to gist;
```

mysqlからexitで出て、

必要なライブラリを入手し、データベース作成を作成します
```shell
$ cd ~/code/git-push-hackathon/MyPoZi/gistapp/
$ composer update
$ php artisan migrate
```

次に.envファイルにGITHUB_CLIENT_IDとGITHUB_CLIENT_SECRETを=の次に追記してください。
```.env
GITHUB_CLIENT_ID=
GITHUB_CLIENT_SECRET=
```
githubAPIの設定時のコールバックは以下のURLにお願いします。
```githubAPIcallback
http://gist.test/auth/login/github/callback

```
最後にvirtualboxから出て、以下のコマンドをお願いします。
```shell
$ vangrant reload
```

http://gist.test/
でアクセス出来ると思います。

### アピールポイント
[gistアプリ本家](https://gist.github.com/)と差別化を図るため、ほとんど日本語にしました。これにより、日本人にも気軽に使うことが出来ると考えられます。
また、bootstrapを用いてレスポンシブデザインにしました。
### 機能
- Gistアプリの紹介ページがある
- postするときにバリデーションチェックをする
- GitHubのOAuthを用いたログインができる
- /gists APIを用いた機能がある
  - gistの一覧表示ができる
  - 自分のプライベートgistを一覧表示できる
  - gistを投稿できる
  

環境構築など、非常に複雑で申し訳ございませんが、よろしくお願い致します。
