//
//  SettingsConverter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/26.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

protocol SettingsConverterProtocol {

    func logOut()
}

class SettingsConverter {

    // MARK: Private

    private let api = GitHubAPI.shared
}

// MARK: - Protocol Interface

extension SettingsConverter: SettingsConverterProtocol {

    func logOut() {

        api.logOut()
    }
}
