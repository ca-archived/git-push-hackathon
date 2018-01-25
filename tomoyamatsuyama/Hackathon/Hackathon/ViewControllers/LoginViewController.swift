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
        guard let loginUrl = URL(string: "https://github.com/login") else { return }
        let request = URLRequest(url: loginUrl)
        self.webViewOfLogin.loadRequest(request)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.loadOfWebView()
    }
}
