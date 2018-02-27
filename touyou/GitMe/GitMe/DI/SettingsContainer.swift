//
//  SettingsContainer.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/26.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

// MARK: - SettingsContainer

class SettingsContainer {

    static let shared = SettingsContainer()

    // MARK: Internal

    func configure(_ viewController: SettingsViewController) {

        let converter = SettingsConverter()

        let presenter = SettingsPresenter(converter: converter)

        viewController.presenter = presenter
    }
}
