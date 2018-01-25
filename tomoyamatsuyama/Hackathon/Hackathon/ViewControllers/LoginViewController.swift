//
//  LoginViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController {
    
    enum Config: String {
        case client_id = "b5107ed03329a1ff1b6f"
        case client_secret = "c7c0e8951efaf9df57d4127f11f8e039b56f5c93"
        case redirect_uri = "hackathon://"
        case scope = "user:follow,repo,notifications,gist,read:org"
    }
    
    @IBOutlet weak var webViewOfLogin: UIWebView!
    
    private func loadOfWebView(){
        guard let loginUrl = URL(string: "https://github.com/login/oauth/authorize?client_id=\(Config.client_id.rawValue)&redirect_uri=\(Config.redirect_uri.rawValue)&scope=\(Config.scope.rawValue)") else { return }
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
        print(callBackUrl)
        return true
    }
}
