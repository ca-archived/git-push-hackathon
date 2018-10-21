import Alamofire
import RxCocoa
import RxSwift
import UIKit

protocol SplashViewProtocol: class {
    var refreshTrigger: Signal<Void> { get }
    var present: Signal<SplashRouter.Route> { get }
}

protocol SplashPresenterProtocol: class {
    var isLoading: Observable<Bool> { get }
//    var isConnectNetWork: Observable<Bool> { get }
    var hasAccessToken: Observable<Bool> { get }
}

protocol SplashInteractorProtocol: class {
    func hasAccessToken() -> Observable<Bool>
}

protocol SplashRouterProtocol {
    func transition(route: SplashRouter.Route)
}
