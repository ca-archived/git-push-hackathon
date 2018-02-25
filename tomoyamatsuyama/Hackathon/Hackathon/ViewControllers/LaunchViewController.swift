//
//  LaunchViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class LaunchViewController: UIViewController {

    @IBOutlet private weak var splashIcon: UIImageView!
    private var timer = Timer()
    private func goToLoginVC() {
        let loginVC = LoginViewController.instantiate()
        self.show(loginVC, sender: nil)
    }
    
    private func goToGettingUserVC() {
        let gettingUserVC = GettingUserViewController.instantiate()
        UIView.transition(
            with:(self.view.window)!,
            duration: 0.4,
            options: .transitionCrossDissolve,
            animations: {
                UIView.setAnimationsEnabled(false)
                self.view.window?.rootViewController = gettingUserVC
                UIView.setAnimationsEnabled(true)
        }, completion: nil
        )
    }
    
    @objc private func accessTokenIsNil() {
        //TODO: Error Handler
        if ApiInfomation.get(key: .acccessToken) != nil {
            self.goToGettingUserVC()
        } else {
            self.goToLoginVC()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        appIcon.image = UIImage(named: "appIcon")
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.accessTokenIsNil), userInfo: nil, repeats: false)
    }

}
