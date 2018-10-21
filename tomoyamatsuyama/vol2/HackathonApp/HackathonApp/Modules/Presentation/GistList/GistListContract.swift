import Alamofire
import RxCocoa
import RxSwift
import UIKit

protocol GistListViewProtocol: class {
    var refreshTrigger: Signal<Void> { get }
    var present: Signal<GistListRouter.Route> { get }
}

protocol GistListPresenterProtocol: class {
    var isLoading: Observable<Bool> { get }
    //    var isConnectNetWork: Observable<Bool> { get }
    var hasAccessToken: Observable<Bool> { get }
}

protocol GistListInteractorProtocol: class {
    func hasAccessToken() -> Observable<Bool>
}

protocol GistListRouterProtocol {
    func transition(route: GistListRouter.Route)
}

