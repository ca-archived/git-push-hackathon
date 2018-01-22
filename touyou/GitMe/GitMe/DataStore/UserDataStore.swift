//
//  UserDataStore.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/22.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation
import OAuthSwift
import RxSwift

protocol UserDataStoreProtocol {

    func fetchUserAuth() -> Observable<GitHubUser>
    func fetchLoginStatus() -> Observable<Bool>
}

class UserDataStore {

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

    // MARK: Private

    private let oauthSwift: OAuth2Swift!
    private let userKey: String = "github_user"

    private func authorize() -> Observable<GitHubUser> {

        return Observable<GitHubUser>.create { [unowned self] (observer: AnyObserver<GitHubUser>) -> Disposable in

            self.oauthSwift.authorizeURLHandler = SafariURLHandler(viewController: UIApplication.shared.topPresentedViewController!, oauthSwift: self.oauthSwift)
            let handle = self.oauthSwift.authorize(
                withCallbackURL: URL(string: "gitme-ios://oauth-callback")!,
                scope: "user,repo",
                state: generateState(withLength: 20),
                success: { credential, response, parameters in

                    UserDefaults.standard.set(credential.oauthToken, forKey: self.userKey)
                    print(response ?? "")
                    print(parameters)
                    observer.onNext(GitHubUser(oauthToken: credential.oauthToken))
                    observer.onCompleted()
                },
                failure: { error in

                    observer.onError(error)
                }
            )

            return Disposables.create {
                handle?.cancel()
            }
        }.share()
    }
}

extension UserDataStore: UserDataStoreProtocol {

    func fetchUserAuth() -> Observable<GitHubUser> {

        guard let gitHubUserToken = UserDefaults.standard.object(forKey: userKey) as? String else {

            return self.authorize()
        }

        return Observable<GitHubUser>.create { (observer: AnyObserver<GitHubUser>) -> Disposable in

            observer.onNext(GitHubUser(oauthToken: gitHubUserToken))
            observer.onCompleted()
            return Disposables.create()
        }.share()
    }

    func fetchLoginStatus() -> Observable<Bool> {

        let loginStatus: Bool = UserDefaults.standard.object(forKey: userKey) != nil

        return Observable<Bool>.create { (observer: AnyObserver<Bool>) -> Disposable in

            observer.onNext(loginStatus)
            observer.onCompleted()
            return Disposables.create()
        }.share()
    }
}
