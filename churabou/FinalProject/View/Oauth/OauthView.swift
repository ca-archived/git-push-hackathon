//
//  LoginView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/09.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import WebKit

final class OauthView: BaseView {

    private (set) var webView = WKWebView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
    }
    
    override func initializeView() {
        backgroundColor = .white
        addSubview(webView)
    }
    
    override func initializeConstraints() {
        webView.snp.makeConstraints { (make) in
            make.edges.equalToSuperview()
        }
    }
}
