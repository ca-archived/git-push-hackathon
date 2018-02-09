//
//  LoginContainer.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/22.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

class LoginContainer {

    // MARK: Internal

    static let shared = LoginContainer()

    func configure(_ viewController: LoginViewController) {

        let converter: LoginConverter = LoginConverter()

        let presenter: LoginPresenter = LoginPresenter(converter: converter)

        viewController.presenter = presenter
    }
}

