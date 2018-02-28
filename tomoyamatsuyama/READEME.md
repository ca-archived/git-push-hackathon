## 開発環境
Xcode 9.2  
Swift 4.0  
Pods 1.4.0

## 使用ライブラリ
Alamofire 4.6.0  
Kingfisher 4.6.1  
RxCocoa 4.1.1  
RxSwift 4.1.1

## 導入方法
1. https://github.com/settings/applications/new にアクセスし、Authorization callback URLに`hackathon://`と記入して登録してください。   
2. `Config.plist`の`client_id`と`client_secret`に、ご自身のclient_idとclient_secretを記入してください。  
![config.plist](https://github.com/tomoyamatsuyama/git-push-hackathon/blob/develop/tomoyamatsuyama/Config_plist.png) 
3. pod installをしてください。  

## Sample
![demo](https://github.com/tomoyamatsuyama/git-push-hackathon/blob/develop/tomoyamatsuyama/Hackathon_sample.gif)
