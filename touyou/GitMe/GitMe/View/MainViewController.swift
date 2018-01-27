//
//  MainViewController.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/22.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit


// MARK: - MainViewController

class MainViewController: UIViewController {

    // MARK: Internal
    var oauthKey: String!

    // MARK: UIViewController

    override func awakeFromNib() {

        super.awakeFromNib()

        MainContainer.shared.configure(self)
    }

    override func viewDidLoad() {

        super.viewDidLoad()


    }


    // MARK: Fileprivate


    // MARK: Private

}

// MARK: - Storyboard Instantiable

extension MainViewController: StoryboardInstantiable {}
