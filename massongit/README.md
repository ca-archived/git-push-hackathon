## 導入手順
1. `git clone --recursive https://github.com/CyberAgent/git-push-hackathon`
1. `cd git-push-hackathon/massongit`
1. 以下のファイルを作成
```app/src/main/res/values/callback_urls.xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="callback_url_scheme" translatable="false">{コールバックURLのプロトコル}</string>
    <string name="callback_url_host" translatable="false">{コールバックURLのホスト名}</string>
    <string name="callback_url_path" translatable="false">/{コールバックURLのパス}</string>
</resources>
```

```app/src/main/res/values/github_apis.xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="client_id" translatable="false">{GitHub APIのClient ID}</string>
    <string name="client_secret" translatable="false">{Git Hub APIのClient Secret}</string>
</resources>

```

1. `cd custom-tabs-client`
1. `git config core.sparsecheckout true`
1. `echo customtabs/ >> ../../.git/modules/massongit/custom-tabs-client/info/sparse-checkout`
1. `echo shared/ >> ../../.git/modules/massongit/custom-tabs-client/info/sparse-checkout`
1. `git read-tree -mu HEAD`
