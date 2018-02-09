//
//  MainPresenter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/27.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift

protocol MainPresenterProtocol {

    var isLoggedIn: Bool { get }
    func reload(_ completion: @escaping () -> Void)
    func loadMore(_ completion: @escaping () -> Void)
}

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
}

// MARK: - DataSource

extension MainPresenter: UICollectionViewDataSource {

    func numberOfSections(in collectionView: UICollectionView) -> Int {

        return 1
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {

        return cellData.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {

        let cell: EventCardCollectionViewCell = collectionView.dequeueReusableCell(forIndexPath: indexPath)

        cell.clipsToBounds = false
        cell.eventTitleLabel.attributedText = cellData[indexPath.row].eventTitle
        cell.timeLabel.text = cellData[indexPath.row].createAt.offsetString
        cell.repositoryTitleLabel.text = cellData[indexPath.row].repositoryName
        cell.iconImageView.kf.setImage(with: cellData[indexPath.row].iconUrl)

        return cell
    }
}

// MARK: - Protocol Interface

extension MainPresenter: MainPresenterProtocol {

    var isLoggedIn: Bool {

        return converter.isLoggedIn
    }

    func reload(_ completion: @escaping () -> Void) {

        self.page = 1
        converter.fetchEvent(at: self.page, every: self.perPage)
            .observeOn(MainScheduler.instance)
            .subscribe { [unowned self] event in

            switch event {
            case .next(let value):

                self.cellData = value
            case .error(let error):

                print(error)
            case .completed:

                break
            }
            completion()
        }.disposed(by: disposeBag)
    }

    func loadMore(_ completion: @escaping () -> Void) {

        self.page += 1
        converter.fetchEvent(at: self.page, every: self.perPage)
            .observeOn(MainScheduler.instance)
            .subscribe { [unowned self] event in

            switch event {
            case .next(let value):

                self.cellData.append(contentsOf: value)
            case .error(let error):

                print(error)
            case .completed:

                break
            }
            completion()
        }.disposed(by: disposeBag)
    }
}
