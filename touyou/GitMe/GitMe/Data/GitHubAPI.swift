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
import RxCocoa

// MARK: - GitHubAPI

class GitHubAPI {

    static let shared = GitHubAPI()

    // MARK: Internal

    /// Check log in or not
    var isLoggedIn: Bool {

        return cache.object(forKey: DefaultKeys.oauthKey.rawValue) != nil
    }

    init() {

        self.oauthSwift = OAuth2Swift(
            consumerKey: GitHubConstant.clientId,
            consumerSecret: GitHubConstant.clientSecret,
            authorizeUrl: "https://github.com/login/oauth/authorize",
            accessTokenUrl: "https://github.com/login/oauth/access_token",
            responseType: "code"
        )
        self.decoder = JSONDecoder()
        self.decoder.dateDecodingStrategy = .iso8601
    }

    /// Log in function
    func logIn() -> Observable<User> {

        if cache.object(forKey: DefaultKeys.oauthKey.rawValue) != nil {

            return self.fetchUser()
        }

        return self.authorize()
    }

    /// Log out function
    func logOut() {

        cache.removeObject(forKey: DefaultKeys.oauthKey.rawValue)
        cache.removeObject(forKey: DefaultKeys.userName.rawValue)
        cache.removeObject(forKey: DefaultKeys.userIcon.rawValue)
    }

    /// Fetch Logged In User Information
    func fetchUser() -> Observable<User> {

        if cache.object(forKey: DefaultKeys.userName.rawValue) != nil && cache.object(forKey: DefaultKeys.userIcon.rawValue) != nil {

            let userName = self.cache.string(forKey: DefaultKeys.userName.rawValue)
            let userIconUrl = self.cache.url(forKey: DefaultKeys.userIcon.rawValue)
            let user = User(login: userName!, avatarUrl: userIconUrl!)
            return Observable<User>.just(user)
        }

        if let url = self.createRequestUrl(.getCurrentUser) {

            return self.session.rx.data(request: URLRequest(url: url)).map { data -> User in

                let user = try self.decoder.decode(User.self, from: data)
                self.cache.set(user.login, forKey: DefaultKeys.userName.rawValue)
                self.cache.set(user.avatarUrl, forKey: DefaultKeys.userIcon.rawValue)
                return user
                }.share()
        }

        return Observable<User>.empty()
    }

    /// Fetch Event List
    func fetchEvents(at page: Int, every perPage: Int) -> Observable<[Event]> {

        if let url = self.createRequestUrl(.getEvents(page, perPage)) {

            return self.session.rx.data(request: URLRequest(url: url)).map { data in

                return try self.decoder.decode([Event].self, from: data)
            }
        }

        return Observable<[Event]>.empty()
    }

    /// Fetch Repository Infomation
    func fetchRepositoryInfo(of url: URL) -> (Observable<Repository>, Observable<Readme>) {

        let readmeUrlBase = url.appendingPathComponent("readme")
        if let repoUrl = self.createRequestUrl(.customRequest(url)),
            let readmeUrl = self.createRequestUrl(.customRequest(readmeUrlBase)) {

            return (
                self.session.rx.data(request: URLRequest(url: repoUrl)).map { data -> Repository in

                    return try self.decoder.decode(Repository.self, from: data)
                },
                self.session.rx.data(request: URLRequest(url: readmeUrl)).map { data -> Readme in

                    return try self.decoder.decode(Readme.self, from: data)
                }
            )
        }

        return (Observable<Repository>.empty(), Observable<Readme>.empty())
    }

    // MARK: Private

    private let oauthSwift: OAuth2Swift!
    private let base: String = "https://api.github.com"
    private let cache = UserDefaults.standard
    private let session = URLSession(configuration: .default)
    private let decoder: JSONDecoder!

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

            print("You must log in first.")
            return nil
        }

        var urlString = self.base

        switch type {
        case .getCurrentUser:
            urlString += "/user?access_token=\(oauthKey)"
        case .getEvents(let page, let perPage):

            guard let userName = cache.object(forKey: DefaultKeys.userName.rawValue) as? String else {

                return nil
            }
            urlString += "/users/\(userName)/received_events?access_token=\(oauthKey)&page=\(page)&per_page=\(perPage)"
        case .customRequest(let url):

            return url.queryAdded(name: "access_token", value: oauthKey)
        }

        return URL(string: urlString)
    }

    // MARK: Private Enumration

    private enum DefaultKeys: String {

        case oauthKey
        case userName
        case userIcon
    }

    private enum RequestURL {

        case getCurrentUser
        case getEvents(Int, Int)
        case customRequest(URL)
    }
}
