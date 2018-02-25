//
//  ViewController.swift
//  yumatakei
//
//  Created by 武井　悠真 on 2018/02/02.
//  Copyright © 2018年 yuma. All rights reserved.
//

var accesstoken: String = ""

import UIKit
import OAuthSwift


class ViewController: UIViewController {
    
    @IBOutlet var login_button: UIButton!
    var webview: UIWebView!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    @IBAction func login() {
        let oauthswift = OAuth2Swift(
            consumerKey:    "340e530f1a38c1ababd4",
            consumerSecret: "ed21c79ee00639d6dc5d0a92e33d33c2262c0d1e",
            authorizeUrl:   "https://github.com/login/oauth/authorize",
            accessTokenUrl: "https://github.com/login/oauth/access_token",
            responseType:   "token"
        )
        oauthswift.authorizeURLHandler = SafariURLHandler(viewController: self, oauthSwift: oauthswift)
        oauthswift.authorize(
            withCallbackURL: URL(string: "yumatakei://oauth-callback")!,
            scope: "notifications",
            state: "github",
            success: { credential, response, parameters in
                print("--------success--------")
                print(credential.oauthToken)
                accesstoken = credential.oauthToken
                let storyboard: UIStoryboard = self.storyboard!
                let timeline = storyboard.instantiateViewController(withIdentifier: "timeline")
                self.present(timeline, animated: true, completion: nil)
                // Do your request
            },
            failure: { error in
                print("--------error--------")
                print(error.localizedDescription)
            }
        )
    }
    
}

