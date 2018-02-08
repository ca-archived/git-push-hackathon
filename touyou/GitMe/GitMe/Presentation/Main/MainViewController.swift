//
//  MainViewController.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/22.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift
import Kingfisher

// MARK: - MainViewController

class MainViewController: UIViewController {

    // MARK: Internal
    var oauthKey: String!
    var accessTokenStr: String {

        if let oauthKey = self.oauthKey {

            return "?access_token=\(oauthKey)"
        } else {

            return ""
        }
    }
    var jsonData: Data!
    var feed: FeedResponse?
    var events: [Event] = []

    func parseJson() {

        let decoder = JSONDecoder()
        decoder.dateDecodingStrategy = .iso8601
        do {
            events = try decoder.decode([Event].self, from: jsonData)
        } catch let error {
            print(error)
        }
        DispatchQueue.main.async {

            self.collectionView.reloadData()
        }
    }

    // MARK: UIViewController

    override func awakeFromNib() {

        super.awakeFromNib()

        MainContainer.shared.configure(self)
    }

    override func viewDidLoad() {

        super.viewDidLoad()

        #if DEBUG
//        UserDefaults.standard.set(nil, forKey: "github_user")
        #endif
    }

    override func viewDidAppear(_ animated: Bool) {

        super.viewDidAppear(true)

        if let oauthKey = UserDefaults.standard.object(forKey: "github_user") as? String {

            self.oauthKey = oauthKey
            let session = URLSession(configuration: URLSessionConfiguration.default)
            session.rx.data(request: URLRequest(url: URL(string: "https://api.github.com/users/touyou/received_events\(accessTokenStr)&page=1&per_page=20")!)).subscribe({ [unowned self] event in

                switch event {
                case .next(let value):
                    self.jsonData = value
                    self.parseJson()
                case .error(let error):
                    print(error)
                case .completed:
                    break
                }
            }).disposed(by: disposeBag)
        } else {

            let loginViewController = LoginViewController.instantiate()
            present(loginViewController, animated: true, completion: nil)
        }
    }


    // MARK: Fileprivate
    @IBOutlet weak var collectionView: UICollectionView! {

        didSet {

            collectionView.register(EventCardCollectionViewCell.self)
            collectionView.dataSource = self
            collectionView.delegate = self
        }
    }


    // MARK: Private

    private let disposeBag = DisposeBag()
}

// MARK: - TableView(あとで別のクラスに移行)

extension MainViewController: UICollectionViewDelegate {


}

extension MainViewController: UICollectionViewDelegateFlowLayout {

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {

        let cellWidth = floor(collectionView.bounds.width * 9 / 10)

        return CGSize(width: cellWidth, height: cellWidth)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {

        return floor(collectionView.bounds.width * 1 / 20)
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {

        return 0
    }

    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {

        return UIEdgeInsets.zero
    }
}

extension MainViewController: UICollectionViewDataSource {

    func numberOfSections(in collectionView: UICollectionView) -> Int {

        return 1
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {

        return events.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {

        let cell: EventCardCollectionViewCell = collectionView.dequeueReusableCell(forIndexPath: indexPath)

        cell.clipsToBounds = false
        cell.eventTitleLabel.text = events[indexPath.row].type.rawValue
        cell.timeLabel.text = events[indexPath.row].createdAt.offsetString
        cell.repositoryTitleLabel.text = events[indexPath.row].repo?.name
        cell.iconImageView.kf.setImage(with: events[indexPath.row].actor.avatarUrl)

        return cell
    }
}

// MARK: - Storyboard Instantiable

extension MainViewController: StoryboardInstantiable {}
