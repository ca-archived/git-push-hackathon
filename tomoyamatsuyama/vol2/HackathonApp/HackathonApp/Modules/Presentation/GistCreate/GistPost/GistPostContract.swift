import Alamofire
import RxCocoa
import RxSwift
import UIKit

protocol GistPostViewProtocol: class {
    var createTrigger: Signal<Void> { get }
    var updateSwitchTrigger: Signal<Bool> { get }
    var presentTrigger: Signal<GistPostRouter.Route> { get }
}

protocol GistPostPresenterProtocol: class {
    var isLoading: Observable<Bool> { get }
    var viewModel: GistCreateModel { get }
}

protocol GistPostInteractorProtocol: class {
    func post(_ gist: GistCreateModel) -> Completable
}

protocol GistPostRouterProtocol {
    func transition(_ route: GistPostRouter.Route)
}
