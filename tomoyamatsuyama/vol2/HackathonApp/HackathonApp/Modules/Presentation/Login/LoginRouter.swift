import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class LoginRouter: LoginRouterProtocol {
    
    private weak var view: LoginViewController!
    
    init(view: LoginViewController) {
        self.view = view
    }
    
    func transition(_ route: Route) {
        switch route {
        case .gistList:
            let gistListViewController = GistListViewBuilder.build()
            view.navigationController?.pushViewController(gistListViewController, animated: true)
        }
    }
}

extension LoginRouter {
    enum Route {
        case gistList
    }
}
