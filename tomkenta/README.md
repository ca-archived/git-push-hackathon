## 開発環境

macOS High Sierra 10.13.3 
Xcode 9.2  
Swift 4.0  
Carthage 0.28.0

## 使用ライブラリ

ishkawa/APIKit 3.1.2  
onevcat/Kingfisher 4.2.0  
OAuthSwift/OAuthSwift 1.2.1  
SwiftyJSON/SwiftyJSON	4.0.0 

## ビルド方法

1. GithubのOAuth Apps のAuthorization callback URLに "githubtest://" を入力して下さい。
2. AppConst.swiftの構造体内の

	- clientID
	- clientSecret

	の返り値のそれぞれに、GithubのOAuth Appsで取得した Client ID, Client Secret を設定して下さい。
		
3. ビルド !!

