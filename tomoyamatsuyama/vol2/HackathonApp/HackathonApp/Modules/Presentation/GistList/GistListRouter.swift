import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistListRouter: GistListRouterProtocol {
    
    private weak var view: GistListViewController!
    
    init(view: GistListViewController) {
        self.view = view
    }
    
    func transition(_ route: Route) {
        let targetViewController = GistCreateViewBuilder.build()
        view.navigationController?.present(targetViewController, animated: true, completion: nil)
    }
}

extension GistListRouter {
    enum Route {
        case createGist
    }
}

