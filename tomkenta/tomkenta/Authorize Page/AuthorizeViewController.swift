//
//  AuthorizeViewController.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/26.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import UIKit
import OAuthSwift

final class AuthorizeViewController: UIViewController {
    private var oauth = OAuth2Swift(consumerKey: AppConst.clientID,
                                    consumerSecret: AppConst.clientSecret,
                                    authorizeUrl: "https://github.com/login/oauth/authorize",
                                    accessTokenUrl: "https://github.com/login/oauth/access_token",
                                    responseType: "token")
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let handler = SafariURLHandler(viewController: self, oauthSwift: oauth)
        oauth.authorizeURLHandler = handler
    }
    
    func auth() {
        oauth.authorize(
            withCallbackURL: URL(string: "tomkenta://testapp-callback")!,
            scope: "repo", state: "sample",
            success: { credential, response, parameters in
                Token.oauthToken = credential.oauthToken
        },
            // TODO: alertのHelper追加
            failure: {[weak self] error in
                guard let wSelf = self else { return }
                let alert = UIAlertController(
                    title: "ログインに失敗しました",
                    message: nil,
                    preferredStyle: .alert)
                
                alert.addAction(UIAlertAction(title: "OK", style: .default))
                self?.present(alert, animated: true, completion: nil)

        }
        )
    }
}
