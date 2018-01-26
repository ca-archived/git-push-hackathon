//
//  LoginViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController, UIWebViewDelegate {
    private let indicator = UIActivityIndicatorView()
    @IBOutlet weak var webViewOfLogin: UIWebView!
    
    @IBAction func viewGoBack(_ sender: Any) {
        self.webViewOfLogin.goBack()
    }
    
    @IBAction func viewGoFoward(_ sender: Any) {
        self.webViewOfLogin.goForward()
    }
    
    @IBAction func viewReload(_ sender: Any) {
        self.webViewOfLogin.reload()
    }
    
    private func loadOfWebView(){
        let config = Config()
        guard let loginUrl = URL(string: "https://github.com/login/oauth/authorize?client_id=\(config.get(key: "client_id"))&redirect_uri=\(config.get(key: "redirect_uri"))&scope=\(config.get(key: "scope"))") else { return }
        let request = URLRequest(url: loginUrl)
        self.webViewOfLogin.loadRequest(request)
    }
    
    func goToHomeVC(){
        let homeVC = HomeViewController.instatiate()
        self.present(homeVC, animated: true, completion: nil)
    }
    
    func webView(_ webView: UIWebView, shouldStartLoadWith request: URLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        guard let callBackUrl = request.url else { return false }
        if callBackUrl.absoluteString.contains("hackathon://?code=") {
            showIndicator(indicator: indicator)
            GithubApiManager.getCodeFromCallBackUrl(callBackUrl: callBackUrl, completion: { isStatus in
                if isStatus {
                    self.goToHomeVC()
                } else {
                    print("error")
                }
                self.stopIndecator(indicator: self.indicator)
            })
        }
        return true
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.loadOfWebView()
    }
}

