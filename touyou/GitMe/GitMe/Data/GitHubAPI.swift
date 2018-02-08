//
//  MainDataStore.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/27.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation
import OAuthSwift
import RxSwift

class GitHubAPI {

    static let shared = GitHubAPI()

    // MARK: Internal

    init() {

        self.oauthSwift = OAuth2Swift(
            consumerKey: GitHubConstant.clientId,
            consumerSecret: GitHubConstant.clientSecret,
            authorizeUrl: "https://github.com/login/oauth/authorize",
            accessTokenUrl: "https://github.com/login/oauth/access_token",
            responseType: "code"
        )
    }

    /// Logged in function
    func logIn() -> Observable<User> {

        if cache.object(forKey: DefaultKeys.oauthKey.rawValue) != nil {

            return self.fetchUser()
        } else {

            return self.authorize()
        }
    }

    /// Fetch Logged In User Information
    func fetchUser() -> Observable<User> {

        if let url = self.createRequestUrl(.getCurrentUser) {

            let session = URLSession(configuration: .default)
            return session.rx.data(request: URLRequest(url: url)).map { data -> User in

                let decoder = JSONDecoder()
                decoder.dateDecodingStrategy = .iso8601
                return try decoder.decode(User.self, from: data)
                }.share()
        } else {

            return Observable<User>.empty()
        }
    }

    /// Fetch Event List
    func fetchEvents(at page: Int, every perPage: Int) -> Observable<[Event]> {

        if let url = self.createRequestUrl(.getEvents(page, perPage)) {

            let session = URLSession(configuration: .default)
            return session.rx.data(request: URLRequest(url: url)).map { data -> [Event] in

                let decoder = JSONDecoder()
                decoder.dateDecodingStrategy = .iso8601
                return try decoder.decode([Event].self, from: data)
                }.share()
        } else {

            return Observable<[Event]>.empty()
        }
    }

    // MARK: Private

    private let oauthSwift: OAuth2Swift!
    private let base: String = "https://api.github.com"
    private let cache = UserDefaults.standard

    /// If there is no auth key, then you should log in first
    private func authorize() -> Observable<User> {

        return Observable<Observable<User>>.create { [unowned self] observer in

            self.oauthSwift.authorizeURLHandler = SafariURLHandler(
                viewController: UIApplication.shared.topPresentedViewController!,
                oauthSwift: self.oauthSwift
            )

            let handle = self.oauthSwift.authorize(
                withCallbackURL: URL(string: "gitme-ios://oauth-callback")!,
                scope: "user,repo",
                state: generateState(withLength: 20),
                success: { credential, _, _ in

                    self.cache.set(credential.oauthToken, forKey: DefaultKeys.oauthKey.rawValue)
                    observer.onNext(self.fetchUser())
                    observer.onCompleted()
            }, failure: { error in

                observer.onError(error)
            })

            return Disposables.create {
                handle?.cancel()
            }
            }.concat().share()
    }

    /// API Access Utility
    private func createRequestUrl(_ type: RequestURL) -> URL? {

        guard let oauthKey = cache.object(forKey: DefaultKeys.oauthKey.rawValue) as? String else {

            print("You must logged in first.")
            return nil
        }

        var urlString = self.base

        switch type {
        case .getCurrentUser:
            urlString += "/user?"
        case .getEvents(let page, let perPage):

            guard let userName = cache.object(forKey: DefaultKeys.oauthKey.rawValue) as? String else {

                return nil
            }
            urlString += "/users/\(userName)/received_events?page=\(page)&per_page=\(perPage)&"
        }

        urlString += "access_token=\(oauthKey)"

        return URL(string: urlString)
    }

    private enum DefaultKeys: String {

        case oauthKey
        case userName
    }

    private enum RequestURL {

        case getCurrentUser
        case getEvents(Int, Int)
    }
}
