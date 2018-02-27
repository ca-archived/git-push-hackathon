//
//  SettingsPresenter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/26.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

protocol SettingsPresenterProtocol: UITableViewDataSource {

    func logOut()
}

class SettingsPresenter: NSObject {

    init(converter: SettingsConverterProtocol) {

        self.converter = converter
    }

    // MARK: Private

    private let converter: SettingsConverterProtocol!
}

// MARK: - DataSource

extension SettingsPresenter {

    func numberOfSections(in tableView: UITableView) -> Int {

        return 1
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {

        return 1
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        let cell = UITableViewCell()
        cell.textLabel?.text = "ログアウト"
        return cell
    }
}

// MARK: - Protocol Interface

extension SettingsPresenter: SettingsPresenterProtocol {

    func logOut() {

        converter.logOut()
    }
}
