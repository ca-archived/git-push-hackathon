//
//  UITabBarController+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/26.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - First ViewController Extensions

extension UITabBarController {

    var firstViewController: UIViewController? {

        if let navigationController = self.viewControllers?.first as? UINavigationController {

            return navigationController.viewControllers.first
        }

        return self.viewControllers?.first
    }
}
