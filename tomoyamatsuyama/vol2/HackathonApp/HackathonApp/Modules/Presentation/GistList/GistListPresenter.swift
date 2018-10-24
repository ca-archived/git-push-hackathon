import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistListPresenter: GistListPresenterProtocol {
    let isLoading: Observable<Bool>
    private let _isLoading = PublishRelay<Bool>()
    
    let viewModel: Observable<GistListViewModel>
    private let _viewModel = PublishRelay<GistListViewModel>()

    private weak var view: GistListViewProtocol!
    private var interactor: GistListInteractorProtocol
    private var router: GistListRouterProtocol
    private let disposeBag = DisposeBag()
    
    init(view: GistListViewProtocol, interactor: GistListInteractorProtocol, router: GistListRouterProtocol) {
        self.view = view
        self.interactor = interactor
        self.router = router
        
        self.isLoading = _isLoading.asObservable()
        self.viewModel = _viewModel.asObservable()
        
        Observable
            .of(view.refreshTrigger.asObservable(), view.dismissTrigger.asObservable())
            .merge()
            .subscribe(onNext: { [weak self] _ in
                guard let `self` = self else { return }
                
//                self._isLoading.accept(true)
                
                interactor.fetchAllGists()
                    .flatMap { response -> Observable<GistListViewModel> in
//                        self._isLoading.accept(false)
                        let vm = GistListTranslator.translate(from: response)
                        return .just(vm)
                    }
                    .bind(to: self._viewModel)
                    .disposed(by: self.disposeBag)
            })
            .disposed(by: disposeBag)
        
        view.present
            .emit(onNext: { route in
                router.transition(route)
            })
            .disposed(by: disposeBag)
    }
}

struct GistListTranslator {
    static func translate(from model: GistList) -> GistListViewModel {
        
        let gists = model.map { GistListViewModel.Gist(title: $0.files.first!.key, createdAt: $0.createdAt, description: $0.description, userIcon: $0.owner.avatarURL) }
        
        let result = GistListViewModel(gists: gists)
        
        return result
    }
}
