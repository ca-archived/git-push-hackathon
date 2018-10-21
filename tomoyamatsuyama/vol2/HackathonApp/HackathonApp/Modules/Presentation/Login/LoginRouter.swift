import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class LoginRouter: LoginRouterProtocol {
    
    private weak var view: LoginViewController!
    
    init(view: LoginViewController) {
        self.view = view
    }
    
    func transition(route: LoginRouter.Route) {
        print(route)
    }
}

extension LoginRouter {
    enum Route {
        case gistList
    }
}
