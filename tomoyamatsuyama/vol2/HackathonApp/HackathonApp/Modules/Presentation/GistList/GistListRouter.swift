import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistListRouter: GistListRouterProtocol {
    
    private weak var view: GistListViewController!
    
    init(view: GistListViewController) {
        self.view = view
    }
    
    func transition(route: GistListRouter.Route) {
        print(route)
    }
}

extension GistListRouter {
    enum Route {
        case createGist
    }
}

