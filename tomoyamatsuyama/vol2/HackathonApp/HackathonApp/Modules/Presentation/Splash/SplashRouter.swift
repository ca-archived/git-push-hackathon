import UIKit

final class SplashRouter: SplashRouterProtocol {
    private weak var view: SplashViewController!
    
    func transition(route: SplashRouter.Route) {
        switch route {
        case .login:
            let loginViewController = LoginViewBuilder.build()
            view.navigationController?.pushViewController(loginViewController, animated: false)
            
        case .gistList:
            let gistListViewController = GistListViewBuilder.build()
            view.navigationController?.pushViewController(gistListViewController, animated: true)
        }
    }
    
    init(view: SplashViewController) {
        self.view = view
    }
}

extension SplashRouter {
    enum Route {
        case login
        case gistList
    }
}
