import Alamofire
import RxCocoa
import RxSwift
import UIKit

protocol SplashViewProtocol: class {
    var refreshTrigger: Signal<Void> { get }
    var presentTrigger: Signal<SplashRouter.Route> { get }
}

protocol SplashPresenterProtocol: class {
    var isLoading: Observable<Bool> { get }
//    var isConnectNetWork: Observable<Bool> { get }
    var isAccessToken: Observable<Bool> { get }
}

protocol SplashInteractorProtocol: class {
    func fetchAccessToken() -> Observable<String?>
}

protocol SplashRouterProtocol {
    func transition(route: SplashRouter.Route)
}
