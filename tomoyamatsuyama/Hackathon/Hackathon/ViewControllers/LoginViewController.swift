//
//  LoginViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
    @IBOutlet weak var webViewOfLogin: UIWebView!
    
    private func loadOfWebView(){
        guard let loginUrl = URL(string: "https://github.com/login/oauth/authorize?client_id=\(Config.Config.client_id.rawValue)&redirect_uri=\(Config.Config.redirect_uri.rawValue)&scope=\(Config.Config.scope.rawValue)") else { return }
        let request = URLRequest(url: loginUrl)
        self.webViewOfLogin.loadRequest(request)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.loadOfWebView()
    }
}

extension LoginViewController: UIWebViewDelegate {
    func webView(_ webView: UIWebView, shouldStartLoadWith request: URLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        guard let callBackUrl = request.url else { return false }
        if callBackUrl.absoluteString.contains("hackathon://?code=") {
            GithubApiManager.getCodeFromCallBackUrl(callBackUrl: callBackUrl)
        }
        return true
    }
}
