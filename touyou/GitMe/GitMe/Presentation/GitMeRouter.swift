//
//  GitMeRouter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/09.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - GitMeRouter

class GitMeRouter: NSObject {

    static var kAssocKeyWindow: String?

    func openLoadingWindow(userInfo: UserInfoViewModel) {

        if objc_getAssociatedObject(UIApplication.shared, &GitMeRouter.kAssocKeyWindow) != nil { return }

        let newWindow = UIWindow()
        newWindow.frame = UIScreen.main.bounds
        newWindow.alpha = 0.0

        let loadingViewController = LoadingViewController.instantiate()
        loadingViewController.userInfo = userInfo
        newWindow.rootViewController = loadingViewController
        newWindow.backgroundColor = UIColor(white: 0, alpha: 0.6)
        newWindow.windowLevel = UIWindowLevelNormal + 5
        newWindow.makeKeyAndVisible()

        objc_setAssociatedObject(UIApplication.shared, &GitMeRouter.kAssocKeyWindow, newWindow, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)

        UIView.transition(with: newWindow, duration: 0.2, options: [.transitionCrossDissolve, .curveEaseIn], animations: {

            newWindow.alpha = 1.0
        }, completion: { _ in })
    }

    func closeLoadingWindow() {

        guard let window = objc_getAssociatedObject(UIApplication.shared, &GitMeRouter.kAssocKeyWindow) as? UIWindow else { return }

        UIView.transition(with: window, duration: 0.3, options: [.transitionCrossDissolve, .curveEaseIn], animations: {

            window.alpha = 0.0
        }, completion: { _ in

            window.rootViewController?.view.removeFromSuperview()
            window.rootViewController = nil
            objc_setAssociatedObject(UIApplication.shared, &GitMeRouter.kAssocKeyWindow, nil, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)

            guard let mainWindow = UIApplication.shared.delegate?.window as? UIWindow else { return }

            mainWindow.makeKeyAndVisible()
        })
    }

    func openLogInView() {

        let logInViewController = LoginViewController.instantiate()
        UIApplication.shared.topPresentedViewController?.present(logInViewController, animated: true, completion: nil)
    }
}
