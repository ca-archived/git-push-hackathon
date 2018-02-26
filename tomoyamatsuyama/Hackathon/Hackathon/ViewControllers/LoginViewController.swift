//
//  LoginViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit
import RxSwift

class LoginViewController: UIViewController, Instantiatable {
    static var storyboardName: String = "LoginViewController"
    private let loginVM = LoginViewModel()
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
    
    private func loadWebViewOfLogin(){
        guard let webViewUrl = Api.Oauth.webViewUrl else { return }
        let request = URLRequest(url: webViewUrl)
        self.webViewOfLogin.loadRequest(request)
    }
    
    private func goToGettingUserVC() {
        self.dismiss(animated: true, completion: nil)
        
    }
    
    private func bindLoginVC() {
        loginVM.isStatus.asObservable()
            .subscribe({_ in
                if self.loginVM.isStatus.value {
                    self.stopIndicator(indicator: self.indicator)
                    self.goToGettingUserVC()
                }
            })
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.loadWebViewOfLogin()
        self.bindLoginVC()
    }
}

extension LoginViewController: UIWebViewDelegate {
    func webView(_ webView: UIWebView, shouldStartLoadWith request: URLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        guard let callBackUrl = request.url else { return false }
        if callBackUrl.absoluteString.contains("hackathon://?code=") {
            self.startIndicator(indicator: indicator)
            self.loginVM.getCodeFromCallBackUrl(callBackUrl: callBackUrl)
        }
        return true
    }
}


