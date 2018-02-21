//
//  LoginConverter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/22.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift

protocol LoginConverterProtocol {

    func logIn() -> Observable<UserInfoViewModel>
}

class LoginConverter {

    // MARK: Private

    private let api = GitHubAPI.shared
}

// MARK: - Protocol Interface

extension LoginConverter: LoginConverterProtocol {

    func logIn() -> Observable<UserInfoViewModel> {

        return api.logIn().map { user in

            let userInfoViewModel = UserInfoViewModel()
            userInfoViewModel.userName = user.login
            userInfoViewModel.iconUrl = user.avatarUrl
            return userInfoViewModel
        }
    }
}
