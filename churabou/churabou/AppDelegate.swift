//
//  AppDelegate.swift
//  churabou
//
//  Created by ちゅーたつ on 2018/02/16.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        configure()
        return true
    }
    
    func configure() {
        UINavigationBar.appearance().barTintColor = UIColor.blackTheme
    }
}

