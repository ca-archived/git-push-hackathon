//
//  TabBarController.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/30.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

class TabBarController: UITabBarController {

    // MARK: Life Cycle

    override func viewDidLoad() {
        super.viewDidLoad()

        let mainViewController = UINavigationController(rootViewController: MainViewController.instantiate())
        mainViewController.title = "Feed"

        self.setViewControllers([mainViewController], animated: false)
    }
}

// MARK: - Storyboard Instantiable

extension TabBarController: StoryboardInstantiable {}
