import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistPostRouter: GistPostRouterProtocol {
    
    private weak var view: GistPostViewController!
    
    init(view: GistPostViewController) {
        self.view = view
    }
    
    func transition(_ route: Route) {
        switch route {
        case .gistList:
//            let targetViewController = GistCreateViewBuilder.build()
            view.dismiss(animated: true) {
                let parentView = self.view.presentingViewController as? GistListViewProtocol
                parentView?.dismissTrigger.accept(())
            }
        }
    }
}

extension GistPostRouter {
    enum Route {
        case gistList
    }
}


