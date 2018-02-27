//
//  UIApplication+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/16.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - Present ViewController Extensions

extension UIApplication {

    var topPresentedViewController: UIViewController? {

        guard var topViewController = UIApplication.shared.keyWindow?.rootViewController else { return nil }

        while let presentedViewController = topViewController.presentedViewController {

            topViewController = presentedViewController
        }

        return topViewController
    }

    var topPresentedNavigationController: UINavigationController? {

        return topPresentedViewController as? UINavigationController
    }

    var topPresentedTabBarController: UITabBarController? {

        return topPresentedViewController as? UITabBarController
    }
}
