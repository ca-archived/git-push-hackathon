//
//  LoadingViewController.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/09.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import Kingfisher

// MARK: - LoadingViewController

class LoadingViewController: UIViewController {

    // MARK: Internal

    var userInfo: UserInfoViewModel! {

        didSet {

            if loginNameLabel != nil {

                loginNameLabel.text = "\(userInfo.userName)でログインします"
                loginIconImageView.kf.setImage(with: userInfo.iconUrl, placeholder: #imageLiteral(resourceName: "feed"))
            }
        }
    }

    // MARK: Life Cycle

    override func viewDidAppear(_ animated: Bool) {

        if userInfo != nil {

            loginNameLabel.text = "\(userInfo.userName)でログインします"
            loginIconImageView.kf.setImage(with: userInfo.iconUrl, placeholder: #imageLiteral(resourceName: "feed"))
        }
    }

    // MARK: Private

    @IBOutlet weak var loginNameLabel: UILabel!
    @IBOutlet weak var loginIconImageView: UIImageView! {

        didSet {

            loginIconImageView.cornerRadius = loginIconImageView.frame.width / 2
            loginIconImageView.clipsToBounds = true
        }
    }
}

// MARK: - Storyboard Instantiable

extension LoadingViewController: StoryboardInstantiable {}

