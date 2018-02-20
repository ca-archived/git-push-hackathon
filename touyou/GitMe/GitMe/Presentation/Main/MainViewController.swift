//
//  MainViewController.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/22.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift

// MARK: - MainViewController

class MainViewController: UIViewController {

    // MARK: Internal

    var presenter: (MainPresenterProtocol & UICollectionViewDataSource)!

    // MARK: Life Cycle

    override func awakeFromNib() {

        super.awakeFromNib()

        MainContainer.shared.configure(self)
    }

    override func viewDidLoad() {

        super.viewDidLoad()

        refreshControl.addTarget(self, action: #selector(self.refresh(_:)), for: .valueChanged)
        self.navigationController?.setupBarColor()
    }

    override func viewDidAppear(_ animated: Bool) {

        super.viewDidAppear(animated)

        if !presenter.isLoggedIn {

            TabBarController.router.openLogInView()
        } else {

            presenter.fetchUser()
            presenter.logInData
                .asObservable()
                .observeOn(MainScheduler.instance)
                .subscribe { [unowned self] event in

                    switch event {
                    case .next(let value):
                        TabBarController.router.openLoadingWindow(userInfo: value)
                        self.presenter.reload { [weak self] in

                            guard let `self` = self else { return }

                            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 0.3, execute: {

                                TabBarController.router.closeLoadingWindow()
                            })
                            self.collectionView.reloadData()
                        }
                    case .error(let error):
                        print(error)
                    case .completed:
                        break
                    }
                }.disposed(by: disposeBag)
        }
    }

    // MARK: Private

    private let disposeBag = DisposeBag()
    private let refreshControl = UIRefreshControl()

    private var isLoading: Bool = false

    @IBOutlet private weak var collectionView: UICollectionView! {

        didSet {

            collectionView.register(EventCardCollectionViewCell.self)
            collectionView.dataSource = presenter
            collectionView.delegate = self
            collectionView.refreshControl = refreshControl
        }
    }

    @objc private func refresh(_ sender: UIRefreshControl) {

        presenter.reload { [weak self] in

            guard let `self` = self else { return }

            sender.endRefreshing()
            self.collectionView.reloadData()
        }
    }
}

// MARK: - CollectionView Delegate

extension MainViewController: UICollectionViewDelegate {

    func scrollViewDidScroll(_ scrollView: UIScrollView) {

        if collectionView.contentOffset.y + collectionView.frame.size.height > collectionView.contentSize.height && collectionView.isDragging && !self.isLoading {

            self.isLoading = true
            presenter.loadMore { [weak self] in

                guard let `self` = self else { return }

                self.isLoading = false
                self.collectionView.reloadData()
            }
        }
    }
}

// MARK: - CollectionView Layout

extension MainViewController: UICollectionViewDelegateFlowLayout {

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {

        let cellWidth = collectionView.bounds.width - 24
        return CGSize(width: cellWidth, height: cellWidth)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {

        return 12.0
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {

        return 0
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {

        return UIEdgeInsets(top: 12.0, left: 0, bottom: 0, right: 0)
    }
}

// MARK: - Storyboard Instantiable

extension MainViewController: StoryboardInstantiable {}
