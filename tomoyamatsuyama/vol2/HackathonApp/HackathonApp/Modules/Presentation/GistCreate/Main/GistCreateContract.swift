import Alamofire
import RxCocoa
import RxSwift
import UIKit

protocol GistCreateViewProtocol: class {
    var presentTrigger: Signal<GistCreateRouter.Route> { get }
}

protocol GistCreatePresenterProtocol: class {

}

protocol GistCreateInteractorProtocol: class {
    func post(_ gist: GistCreateModel) -> Completable
}

protocol GistCreateRouterProtocol {
    func transition(_ route: GistCreateRouter.Route)
}

struct GistCreateModel {
    var title: String?
    var content: String?
    var description: String?
    var isPublic: Bool?
}
