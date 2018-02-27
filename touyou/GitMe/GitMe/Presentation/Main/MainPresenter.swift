//
//  MainPresenter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/27.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift
import PINCache
import PINRemoteImage
import Down

// MARK: - MainPresenterProtocol

protocol MainPresenterProtocol: UITableViewDataSource {

    var isLoggedIn: Bool { get }
    var logInData: Variable<UserInfoViewModel> { get }
    var itemCount: Int { get }

    func fetchUser()
    func reload(_ completion: @escaping (Int?) -> Void)
    func loadMore(_ completion: @escaping (Int?) -> Void)
    func logOut()
}

// MARK: - MainPresenter

class MainPresenter: NSObject {

    init(converter: MainConverterProtocol) {

        self.converter = converter
    }

    // MARK: Private

    private let converter: MainConverterProtocol!
    private let disposeBag = DisposeBag()
    private let perPage: Int = 30

    private var page: Int = 1
    private var cellData: [EventCellViewModel] = []
    private(set) var logInData = Variable<UserInfoViewModel>(UserInfoViewModel())

    private func subscribeEventCellViewModel(_ completion: @escaping (Int?) -> Void) {

        for i in 0 ..< cellData.count {

            if cellData[i].repositoryDescription == nil {

                cellData[i].repoObservable
                    .observeOn(MainScheduler.instance)
                    .subscribe { [unowned self] event in

                        switch event {
                        case .next(let value):

                            self.cellData[i].repositoryDescription = value.repositoryDescription
                            self.cellData[i].repositoryInfo = value.repoInfo
                            completion(i)
                        case .error(let error):

                            print(error)
                        case .completed:

                            break
                        }
                    }.disposed(by: disposeBag)
            }
            if cellData[i].readmeUrl == nil {

                cellData[i].readmeObservable
                    .observeOn(MainScheduler.instance)
                    .subscribe { [unowned self] event in

                        switch event {
                        case .next(let value):

                            self.cellData[i].readmeUrl = value
                            completion(i)
                        case .error(let error):

                            print(error)
                        case .completed:

                            break
                        }
                    }.disposed(by: disposeBag)
            }
        }
    }
}

// MARK: - DataSource

extension MainPresenter {

    func numberOfSections(in tableView: UITableView) -> Int {

        return 1
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {

        return cellData.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        let cell: EventCardTableViewCell = tableView.dequeueReusableCell(forIndexPath: indexPath)

        cell.eventLabel.attributedText = cellData[indexPath.row].eventTitle
        cell.timeLabel.text = cellData[indexPath.row].createAt.offsetString
        cell.repoNameLabel.text = cellData[indexPath.row].repositoryName
        cell.iconImageView.pin_setImage(from: cellData[indexPath.row].iconUrl, placeholderImage: #imageLiteral(resourceName: "placeholder"))
        cell.repoDescLabel.text = cellData[indexPath.row].repositoryDescription
        cell.repoInfoLabel.text = cellData[indexPath.row].repositoryInfo
        cell.readmeUrl = cellData[indexPath.row].readmeUrl

        cell.isShowReadme = cellData[indexPath.row].isShowReadme
        cell.completion = {

            self.cellData[indexPath.row].isShowReadme = !self.cellData[indexPath.row].isShowReadme
            tableView.reloadData(at: indexPath.row)
        }

        return cell
    }
}

// MARK: - Protocol Interface

extension MainPresenter: MainPresenterProtocol {

    var isLoggedIn: Bool {

        return converter.isLoggedIn
    }

    var itemCount: Int {

        return cellData.count
    }

    func fetchUser() {

        converter.fetchLoginUserInfo()
            .observeOn(MainScheduler.instance)
            .subscribe { [weak self] event in

                guard let `self` = self else { return }

                switch event {
                case .next(let value):

                    self.logInData.value = value
                case .error(let error):

                    print(error)
                default:
                    break
                }
            }.disposed(by: disposeBag)
    }

    func reload(_ completion: @escaping (Int?) -> Void) {

        self.page = 1
        converter.fetchEvent(at: self.page, every: self.perPage)
            .observeOn(MainScheduler.instance)
            .subscribe { [unowned self] event in

                switch event {
                case .next(let value):

                    self.cellData = value
                    self.subscribeEventCellViewModel(completion)
                case .error(let error):

                    print(error)
                case .completed:

                    break
                }
                completion(nil)
            }.disposed(by: disposeBag)
    }

    func loadMore(_ completion: @escaping (Int?) -> Void) {

        self.page += 1
        converter.fetchEvent(at: self.page, every: self.perPage)
            .observeOn(MainScheduler.instance)
            .subscribe { [unowned self] event in

                switch event {
                case .next(let value):

                    self.cellData.append(contentsOf: value)
                    self.subscribeEventCellViewModel(completion)
                case .error(let error):

                    print(error)
                case .completed:

                    break
                }
                completion(nil)
            }.disposed(by: disposeBag)
    }

    func logOut() {

        cellData = []
    }
}
