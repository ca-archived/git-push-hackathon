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
        let targetViewController = GistCreateViewBuilder.build()
        view.navigationController?.present(targetViewController, animated: true, completion: nil)
    }
}

extension GistPostRouter {
    enum Route {
        case gistList
    }
}


