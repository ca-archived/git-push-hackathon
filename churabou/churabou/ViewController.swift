//
//  ViewController.swift
//  churabou
//
//  Created by ちゅーたつ on 2018/02/16.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidAppear(_ animated: Bool) {
        let c = LoginViewController()
        let n = UINavigationController(rootViewController: c)
        present(n, animated: false, completion: nil)
    }
}
