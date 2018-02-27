//
//  SettingsConverter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/26.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation
import RxSwift

// MARK: - SettingsConverterProtocol

protocol SettingsConverterProtocol {

    func logOut()
    func fetchLoginUserInfo() -> Observable<UserInfoViewModel>
}

// MARK: - SettingsConverter

class SettingsConverter {

    // MARK: Private

    private let api = GitHubAPI.shared
}

// MARK: - Protocol Interface

extension SettingsConverter: SettingsConverterProtocol {

    func logOut() {

        api.logOut()
    }

    func fetchLoginUserInfo() -> Observable<UserInfoViewModel> {

        return api.logIn().map { user in

            let userInfoViewModel = UserInfoViewModel()
            userInfoViewModel.userName = user.login
            userInfoViewModel.iconUrl = user.avatarUrl
            return userInfoViewModel
        }
    }
}
