import UIKit

final class SplashRouter: SplashRouterProtocol {
    private weak var view: SplashViewController!
    
    init(view: SplashViewController) {
        self.view = view
    }
    
    func transition(route: SplashRouter.Route) {
        switch route {
        case .login:
            print("loginView")
        case .gistList:
            print("gistList")
        }
    }
}

extension SplashRouter {
    enum Route {
        case login
        case gistList
    }
}
