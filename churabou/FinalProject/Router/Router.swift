//
//  Router.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit


class Router {
    weak var controller: UIViewController?
}

final class AppRouter: Router {
    
    static func makeRootViewController(isLogin: Bool) -> UIViewController {
        return isLogin ? makeLoginUserController() : makeTopController()
    }
    
    static func makeTopController() -> UIViewController {
        let viewModel = TopViewModel()
        let router = AppRouter()
        let controller = TopController(viewModel: viewModel, router: router)
        router.controller = controller
        return controller
    }
    
    static func makeLoginUserController() -> UIViewController {
        let session = LoginUserSession()
        let viewModel = LoginUserViewModel(session: session)
        let router = AppRouter()
        let controller = LoginUserController(viewModel: viewModel, router: router)
        router.controller = controller
        return controller
    }
    
    static func makeUserGistController(user: User) -> UIViewController {
        let router = AppRouter()
        let viewModel = UserGistViewModel(user: user, router: router)
        let controller = UserGistController(user: user, viewModel: viewModel, router: router)
        router.controller = controller
        return controller
    }
    
    static func makeFollowerListController(user: User) -> UIViewController {
        let session = FollowerSession()
        let reactor = FollowerListReactor(user: user, session: session)
        let router = AppRouter()
        let controller = FollowerListController(reactor: reactor, router: router)
        router.controller = controller
        return controller
    }
    
    static func makeGistListController() -> UIViewController {
        let session =  GistSession()
        let reactor = GistListReactor(session: session)
        let router = AppRouter()
        let controller = GistListController(reactor: reactor, router: router)
        router.controller = controller
        return controller
    }
}

protocol TopRouter {
    func showOuth()
    func showLoginUser()
}

extension AppRouter: TopRouter {
    
    func showOuth() {
        let viewModel = OauthViewModel(api: APIClient(), service: UserDefaults.standard)
        let dataSource = OauthViewDataSource(viewModel: viewModel)
        let login = OauthController(viewModel: viewModel, dataSource: dataSource)
        let n = UINavigationController(rootViewController: login)
        controller?.present(n, animated: true, completion: nil)
    }
    
    func showLoginUser() {
        let c = AppRouter.makeLoginUserController()
        controller?.present(c, animated: false, completion: nil)
    }
}


protocol LoginUserRouter {
    func showHome(user: User)
}

extension AppRouter: LoginUserRouter {
    
    func showHome(user: User) {
        let c = HomeTabController(user: user)
        controller?.present(c, animated: true, completion: nil)
    }
}

protocol FollowerListRouter {
    func showUser(_ user: User)
}

extension AppRouter: FollowerListRouter {
    
    func showUser(_ user: User) {
        let c = AppRouter.makeUserGistController(user: user)
        controller?.navigationController?.pushViewController(c, animated: true)
    }
}


protocol UserGistRouter {
    func showGist(gist: Gist)
    func showFollower(user: User)
}

extension AppRouter: UserGistRouter {
    
    func showFollower(user: User) {
        let c = AppRouter.makeFollowerListController(user: user)
        controller?.navigationController?.pushViewController(c, animated: true)
    }
    
    func showGist(gist: Gist) {
        let c = ShowGistController()
        c.gist = gist
        controller?.navigationController?.pushViewController(c, animated: true)
    }
}

protocol GistListRouter {
    func showGist(gist: Gist)
}

extension AppRouter: GistListRouter {}
