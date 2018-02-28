//
//  SettingsPresenter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/26.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift
import PINCache
import PINRemoteImage

// MARK: - SettingsPresenterProtocol

protocol SettingsPresenterProtocol: UITableViewDataSource {

    func logOut()
    func fetchUser(_ completion: @escaping () -> Void)
}

// MARK: - SettingsPresenter

class SettingsPresenter: NSObject {

    init(converter: SettingsConverterProtocol) {

        self.converter = converter
    }

    // MARK: Private

    private let converter: SettingsConverterProtocol!
    private let disposeBag = DisposeBag()

    private var userInfo: UserInfoViewModel?
}

// MARK: - DataSource

extension SettingsPresenter {

    func numberOfSections(in tableView: UITableView) -> Int {

        return 2
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {

        return 1
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        if indexPath.section == 1 {

            let cell = UITableViewCell()
            cell.textLabel?.text = "ログアウト"
            return cell
        }

        let cell: UserInfoTableViewCell = tableView.dequeueReusableCell(forIndexPath: indexPath)

        cell.iconImageView.pin_setImage(from: userInfo?.iconUrl, placeholderImage: #imageLiteral(resourceName: "placeholder"))
        cell.userNameLabel.text = userInfo?.userName

        return cell
    }
}

// MARK: - Protocol Interface

extension SettingsPresenter: SettingsPresenterProtocol {
    
    func logOut() {

        converter.logOut()
    }

    func fetchUser(_ completion: @escaping () -> Void) {

        converter.fetchLoginUserInfo()
            .observeOn(MainScheduler.instance)
            .subscribe { [weak self] event in
                guard let `self` = self else { return }

                switch event {
                case .next(let value):

                    self.userInfo = value
                    completion()
                case .error(let error):

                    print(error)
                default:
                    break
                }
            }.disposed(by: disposeBag)
    }
}
