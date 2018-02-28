//
//  ViewController.swift
//  tomoyukiHAYAKAWA
//
//  Created by Tomoyuki Hayakawa on 2018/01/21.
//  Copyright © 2018年 Tomoyuki Hayakawa. All rights reserved.
//

import UIKit
import OAuthSwift
import Alamofire

class ViewController: UIViewController {
    
    @IBOutlet var userToken: UILabel!
    @IBOutlet var infoTextView: UITextView!
    
    let saveData: UserDefaults = UserDefaults.standard
    
    var oauthSwift: OAuth2Swift!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        print("アプリ起動")
        let token: String!
        token = saveData.object(forKey: "oauthToken") as? String
        userToken.text = token
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func singInBtn(_ sender: Any) {
        
        oauthSwift = OAuth2Swift(
            consumerKey: "35372fd64ec013375a66",
            consumerSecret: "d5e895143bc269f6a378c48b0c7f897837bd72c8",
            authorizeUrl: "https://github.com/login/oauth/authorize",
            accessTokenUrl: "https://github.com/login/oauth/access_token",
            responseType: "token"
        )
        
        print("認証前")
        
        self.oauthSwift.allowMissingStateCheck = false
        self.oauthSwift.authorizeURLHandler = SafariURLHandler(viewController: self, oauthSwift: self.oauthSwift)
        
        oauthSwift.authorize(
            withCallbackURL: URL(string: "hayakawaapp://oauth-callback")!,
            scope: "", state: "GitHub",
            success: { credential, response, parameters in
                print("認証成功")
                print(credential.oauthToken)
                // やりたいことをここに書く
                self.saveData.setValue(credential.oauthToken, forKey: "oauthToken")
                self.userToken.text = self.saveData.object(forKey: "oauthToken") as? String
                
                // JSON リクエスト送信とGET
                Alamofire.request("https://api.github.com/user", method: .get, parameters:["access_token": credential.oauthToken]).responseJSON(completionHandler: {response in
                    if let json = response.result.value {
                        self.infoTextView.text = (json as AnyObject).description
                    }
                })
        },
            failure: { error in
                print(error.localizedDescription)
                print("認証失敗")
            }
        )
    }
}

