//
//  ShowGistCell.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/15.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import WebKit


final class ShowGistCell: BaseCollectionViewCell {
    
//    private var starButton = UIButton().apply { it in
//        it.translatesAutoresizingMaskIntoConstraints = false
//        it.setTitle("star", for: .normal)
//        it.setTitleColor(.white, for: .normal)
//        it.layer.cornerRadius = 3
//        it.clipsToBounds = true
//        it.backgroundColor = .cyan
//    }
//    
//    starButton.snp.makeConstraints { make in
//    make.top.equalTo(10)
//    make.right.equalTo(-10)
//    make.width.equalTo(80)
//    make.height.equalTo(30)
//    }
//
    

    private var file: Gist.File?
    
    private lazy var webView = WKWebView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.navigationDelegate = self
    }
    
    func configure(file: Gist.File, showRow: Bool) {
        
        if showRow, let url = URL(string: file.rawUrl) {
            webView.load(URLRequest(url: url))
            return
        }
        self.file = file
        if let htmlData = Bundle.main.path(forResource: "gist", ofType: "html") {
            webView.load(URLRequest(url: URL(fileURLWithPath: htmlData)))
        }
    }
    
    override func initializeView() {
        contentView.addSubview(webView)
    }
    
    override func initializeConstraints() {
        webView.snp.makeConstraints { $0.edges.equalToSuperview() }
    }
}


extension ShowGistCell: WKNavigationDelegate {
    
    func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        
        guard let file = self.file, let url = URL(string: file.rawUrl) else {
            return
        }
        
        let paths = url.pathComponents
        let user = paths[1]
        let id = paths[2]
        let script = "loadGist('\(user)','\(id)');"
        webView.evaluateJavaScript(script, completionHandler: nil)
    }
}
