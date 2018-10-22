//
//  LoginViewDataSource.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/09.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import RxSwift
import WebKit

final class OauthViewDataSource: NSObject {
    
    private let viewModel: OauthViewModel
    
    init(viewModel: OauthViewModel) {
        self.viewModel = viewModel
    }
}

extension OauthViewDataSource: WKNavigationDelegate {
    
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Swift.Void) {
        guard let url = navigationAction.request.url else {
            decisionHandler(.cancel)
            return
        }
        
        if url.absoluteString.hasPrefix(AppConfig.callbackUrl) {
            let code = URLComponents(string: url.absoluteString)?
                .queryItems?
                .filter { $0.name == "code" }
                .first?
                .value
            
            print("oauthのcodeを取得しました")
            viewModel.requestAccessToken.onNext(code!)
        }
        decisionHandler(.allow)
    }
}
