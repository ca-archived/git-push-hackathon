//
//  LoginViewControllerExtension.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

extension LoginViewController: UIWebViewDelegate {
    func webView(_ webView: UIWebView, shouldStartLoadWith request: URLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        guard let callBackUrl = request.url else { return false }
        if callBackUrl.absoluteString.contains("hackathon://?code=") {
            GithubApiManager.getCodeFromCallBackUrl(callBackUrl: callBackUrl, completion: { isStatus in
                if isStatus {
                    self.goToHomeVC()
                } else {
                    print("error")
                }
            })
        }
        return true
    }
}
