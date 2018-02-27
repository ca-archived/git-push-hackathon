//
//  MainContainer.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/27.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

// MARK: - MainContainer

class MainContainer {

    static let shared = MainContainer()

    // MARK: Internal

    func configure(_ viewController: MainViewController) {

        let converter: MainConverter = MainConverter()

        let presenter: MainPresenter = MainPresenter(converter: converter)

        viewController.presenter = presenter
    }
}
