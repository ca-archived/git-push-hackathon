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
    private let disposedBag = DisposeBag()
    private let indicator = UIActivityIndicatorView()
    @IBOutlet private weak var footerView: UIView!
    @IBOutlet private weak var webViewOfLogin: UIWebView!
    
    @IBAction func viewGoBack(_ sender: Any) {
        webViewOfLogin.goBack()
    }
    
    @IBAction func viewGoFoward(_ sender: Any) {
        webViewOfLogin.goForward()
    }
    
    @IBAction func viewReload(_ sender: Any) {
        loadWebViewOfLogin()
    }
    
    private func showAlert() {
        let alert = UIAlertController(title: "ネットワークエラー", message: "ネットワーク環境をご確認ください", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true, completion: nil)
    }
    
    private func loadWebViewOfLogin(){
        guard let webViewUrl = Api.Oauth.webViewUrl else { return }
        let request = URLRequest(url: webViewUrl)
        webViewOfLogin.loadRequest(request)
    }
    
    private func goToGettingUserVC() {
        dismiss(animated: true, completion: nil)
        
    }
    
    private func bindLoginVC() {
        loginVM.isStatus.asObservable()
            .filter{ $0 }
            .subscribe({ [weak self] _ in
                guard let `self` = self else { return }
                self.stopIndicator(indicator: self.indicator)
                self.goToGettingUserVC()
            })
            .disposed(by: disposedBag)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadWebViewOfLogin()
        bindLoginVC()
    }
}

extension LoginViewController: UIWebViewDelegate {
    func webView(_ webView: UIWebView, shouldStartLoadWith request: URLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        guard let callBackUrl = request.url else { return false }
        if callBackUrl.absoluteString.contains("hackathon://?code=") {
            startIndicator(indicator: indicator)
            loginVM.getCodeFromCallBackUrl(callBackUrl: callBackUrl)
        }
        return true
    }
    
    func webView(_ webView: UIWebView, didFailLoadWithError error: Error) {
        showAlert()
    }
}


