//
//  UINavigationController+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/09.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

extension UINavigationController {

    func setupBarColor() {

        self.navigationBar.barTintColor = UIColor.GitMe.darkGray
        self.navigationBar.tintColor = UIColor.white
        self.navigationBar.titleTextAttributes = [.foregroundColor: UIColor.white]
        self.navigationBar.backgroundColor = UIColor.white
    }
}
