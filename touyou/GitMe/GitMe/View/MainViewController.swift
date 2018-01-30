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

    func parseJson() {

        let decoder = JSONDecoder()
        feed = try? decoder.decode(FeedResponse.self, from: jsonData)
        let session = URLSession(configuration: URLSessionConfiguration.default)
        session.rx.data(request: URLRequest(url: (feed?.currentUserPublicUrl?.queryAdded(name: "access_token", value: oauthKey))!)).subscribe({ event in

            switch event {
            case .next(let value):
                let parser = XMLParser(data: value)
                
            default:
                break
            }
        })
    }

    // MARK: UIViewController

    override func awakeFromNib() {

        super.awakeFromNib()

        MainContainer.shared.configure(self)
    }

    override func viewDidLoad() {

        super.viewDidLoad()

//        #if DEBUG
//        UserDefaults.standard.set(nil, forKey: "github_user")
//        #endif
    }

    override func viewDidAppear(_ animated: Bool) {

        super.viewDidAppear(true)

        if let oauthKey = UserDefaults.standard.object(forKey: "github_user") as? String {

            self.oauthKey = oauthKey
            let session = URLSession(configuration: URLSessionConfiguration.default)
            session.rx.data(request: URLRequest(url: URL(string: "https://api.github.com/feeds\(accessTokenStr)")!)).subscribe({ [unowned self] event in

                switch event {
                case .next(let value):
                    self.jsonData = value
                    self.parseJson()
                case .error(let error):
                    print(error.localizedDescription)
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


    // MARK: Private

    private let disposeBag = DisposeBag()
}

// MARK: - Storyboard Instantiable

extension MainViewController: StoryboardInstantiable {}
