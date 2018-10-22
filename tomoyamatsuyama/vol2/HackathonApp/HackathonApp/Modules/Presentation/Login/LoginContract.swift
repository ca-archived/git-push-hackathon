import Alamofire
import RxCocoa
import RxSwift
import UIKit

protocol LoginViewProtocol: class {
    var redirectTrigger: Signal<URLRequest> { get }
    var transition: Signal<LoginRouter.Route> { get }
}

protocol LoginPresenterProtocol: class {
    var status: Observable<LoginPresenter.Status> { get }
}

protocol LoginInteractorProtocol: class {
    func fetchAccessToken(with code: String) -> Observable<Authorization>
}

protocol LoginRouterProtocol {
    func transition(_ route: LoginRouter.Route)
}
