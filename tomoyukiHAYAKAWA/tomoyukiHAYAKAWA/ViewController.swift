//
//  ViewController.swift
//  tomoyukiHAYAKAWA
//
//  Created by Tomoyuki Hayakawa on 2018/01/21.
//  Copyright © 2018年 Tomoyuki Hayakawa. All rights reserved.
//

import UIKit
import OAuthSwift

class ViewController: UIViewController {
    
    @IBOutlet var userToken: UILabel!
    @IBOutlet var infoTextView: UITextView!
    
    let saveData: UserDefaults = UserDefaults.standard
    
    //var oauthSwift: OAuth2Swift!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
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
        
        let oauthSwift = OAuth2Swift(
            consumerKey: "35372fd64ec013375a66",
            consumerSecret: "d5e895143bc269f6a378c48b0c7f897837bd72c8",
            authorizeUrl: "https://github.com/login/oauth/authorize",
            responseType: "token"
        )
        
        print("認証前")
        
        let handle = oauthSwift.authorize(
            withCallbackURL: URL(string: "hayakawaapp://oauth-callback")!,
            scope: "", state: "GitHub",
            success: { credential, response, parameters in
                print("認証成功")
                print(credential.oauthToken)
                // やりたいこと
                self.saveData.setValue(credential.oauthToken, forKey: "oauthToken")
                self.userToken.text = self.saveData.object(forKey: "oauthToken") as? String
        },
            failure: { error in
                print(error.localizedDescription)
                print("認証失敗")
            }
        )
    }
}

