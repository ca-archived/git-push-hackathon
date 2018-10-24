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
            view.dismiss(animated: true) {
                let notification = Notification.Name(NotificationName.dismissGistCreate.name)
                
                NotificationCenter.default.post(name: notification, object: nil)
            }
        }
    }
}

extension GistPostRouter {
    enum Route {
        case gistList
    }
}


