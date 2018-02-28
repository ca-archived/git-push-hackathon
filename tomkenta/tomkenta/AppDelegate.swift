//
//  AppDelegate.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/25.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import UIKit
import OAuthSwift

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
        window = UIWindow(frame: UIScreen.main.bounds)
        let controller = rootViewController()
        window?.rootViewController = controller
        window?.backgroundColor = .white
        window?.makeKeyAndVisible()
        
        if controller is AuthorizeViewController {
            (controller as? AuthorizeViewController)?.auth()
        }

        return true
    }
    
    private func rootViewController() -> UIViewController {
        if Token.oauthToken == nil {
            return AuthorizeViewController()
        }
        return EventListPageViewController()
    }
    
    public func switchRootControllerAfterLogin() {
        let eventListController = EventListPageViewController()
        UIView.transition(with: window!,
                          duration: 0.7,
                          options: .transitionCrossDissolve,
                          animations: {
                            let oldState: Bool = UIView.areAnimationsEnabled
                            UIView.setAnimationsEnabled(false)
                            self.window?.rootViewController = eventListController
                            UIView.setAnimationsEnabled(oldState)
        },
                          completion: nil)
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplicationOpenURLOptionsKey : Any] = [:]) -> Bool {
        OAuthSwift.handle(url: url)
        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
        // Saves changes in the application's managed object context before the application terminates.
    }
}
