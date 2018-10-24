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
        switch route {
        case .dismiss:
            view.dismiss(animated: true, completion: nil)
            
        case .addFile(let viewModel):
            let viewController = AddFileViewBuilder.build(with: viewModel)
            view.navigationController?.pushViewController(viewController, animated: true)
        }
    }
}

extension GistCreateRouter {
    enum Route {
        case dismiss
        case addFile(GistCreateModel)
    }
}
