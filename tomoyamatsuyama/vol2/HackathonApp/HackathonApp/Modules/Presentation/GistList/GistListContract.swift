import Alamofire
import RxCocoa
import RxSwift
import UIKit

protocol GistListViewProtocol: class {
    var refreshTrigger: Signal<Void> { get }
    var present: Signal<GistListRouter.Route> { get }
    var dismissTrigger: PublishRelay<Void> { get }
}

protocol GistListPresenterProtocol: class {
    var isLoading: Observable<Bool> { get }
    var viewModel: Observable<GistListViewModel> { get }
}

protocol GistListInteractorProtocol: class {
    func fetchAllGists() -> Observable<GistList>
}

protocol GistListRouterProtocol {
    func transition(_ route: GistListRouter.Route)
}

struct GistListViewModel {
    let gists: [Gist]
    
    struct Gist {
        let title: String
        let createdAt: String
        let description: String
        let userIcon: URL
    }
}
