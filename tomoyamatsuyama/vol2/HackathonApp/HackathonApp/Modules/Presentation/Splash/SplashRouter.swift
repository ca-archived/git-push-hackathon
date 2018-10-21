import UIKit

final class SplashRouter: SplashRouterProtocol {
    private weak var view: SplashViewController!
    
    func transition(route: SplashRouter.Route) {
        switch route {
        case .login:
            let loginViewController = LoginViewBuilder.build()
            view.present(loginViewController, animated: false, completion: nil)
        case .gistList:
            print("gistList")
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
