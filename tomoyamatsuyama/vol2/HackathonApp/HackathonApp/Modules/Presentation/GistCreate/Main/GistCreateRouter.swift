import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistCreateRouter: GistCreateRouterProtocol {
    private weak var view: GistCreateViewController!
    
    init(view: GistCreateViewController) {
        self.view = view
    }
    
    func transition(_ route: Route) {
        view.dismiss(animated: true, completion: nil)
    }
}

extension GistCreateRouter {
    enum Route {
        case dismiss
    }
}
