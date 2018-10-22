import Alamofire
import RxCocoa
import RxSwift
import UIKit

protocol GistCreateViewProtocol: class {
    var refreshTrigger: Signal<Void> { get }
    var createTrigger: Signal<GitstCreateModel> { get }
    var presentTrigger: Signal<GistCreateRouter.Route> { get }
}

protocol GistCreatePresenterProtocol: class {
    var isLoading: Observable<Bool> { get }
    var viewModel: Observable<GistCreateViewModel> { get }
}

protocol GistCreateInteractorProtocol: class {
    func post(_ gist: GitstCreateModel) -> Completable
}

protocol GistCreateRouterProtocol {
    func transition(_ route: GistCreateRouter.Route)
}

struct GistCreateViewModel {
    
    let sections: [Section] = [.init(items: [.description, .scope]),
                               .init(items: [.file])]
    
    struct Section {
        let items: [Item]
        
        enum Item {
            case description
            case scope
            case file
            
            var viewModel: GistCreateCellProtocol {
                switch self {
                case .description:
                    return DescriptionCellViewModel(content: "")
                case .scope:
                    return ScopeCellViewModel(isOn: true)
                case .file:
                    return AddFileViewModel(content: "")
                }
            }
        }
    }
}

protocol GistCreateCellProtocol { }

struct DescriptionCellViewModel: GistCreateCellProtocol {
    let content: String
}

struct ScopeCellViewModel: GistCreateCellProtocol {
    let isOn: Bool
}

struct AddFileViewModel: GistCreateCellProtocol {
    let content: String
    
}

struct GitstCreateModel {
    let title: String
    let content: String
    let description: String
    let isPublic: Bool
}
