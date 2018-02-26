//
//  TabBarController.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/30.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

class TabBarController: UITabBarController {

    static let router = GitMeRouter()

    // MARK: Life Cycle

    override func viewDidLoad() {
        super.viewDidLoad()

        let mainViewController = UINavigationController(rootViewController: MainViewController.instantiate())
        mainViewController.title = "Feeds"
        mainViewController.tabBarItem.image = UIImage(named: "feed")
        let settingsViewController = UINavigationController(rootViewController: SettingsViewController.instantiate())
        settingsViewController.title = "Settings"
        settingsViewController.tabBarItem.image = UIImage(named: "settings")

        self.viewControllers = [mainViewController, settingsViewController]
    }
}

// MARK: - Storyboard Instantiable

extension TabBarController: StoryboardInstantiable {}
