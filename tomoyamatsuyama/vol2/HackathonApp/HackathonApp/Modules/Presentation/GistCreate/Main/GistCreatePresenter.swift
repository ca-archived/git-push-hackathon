import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistCreatePresenter: GistCreatePresenterProtocol {
    private weak var view: GistCreateViewProtocol!
    private var interactor: GistCreateInteractorProtocol
    private var router: GistCreateRouterProtocol
    
    private let disposeBag = DisposeBag()
    
    let isLoading: Observable<Bool>
    private let _isLoading = PublishRelay<Bool>()
    
    var viewModel: Observable<GistCreateViewModel> {
        return _viewModel.asObservable()
    }
    private let _viewModel = BehaviorRelay<GistCreateViewModel>(value: GistCreateViewModel())
    
    init(view: GistCreateViewProtocol,
         interactor: GistCreateInteractorProtocol,
         router: GistCreateRouterProtocol) {
        
        self.view = view
        self.interactor = interactor
        self.router = router
        
        self.isLoading = _isLoading.asObservable()
        
        view.refreshTrigger
            .emit(onNext: { [weak self] _ in
                guard let `self` = self else { return }
                self._viewModel.accept(GistCreateViewModel())
            })
        
        view.createTrigger
            .emit(onNext: { gist in
                interactor.post(gist)
            })
            .disposed(by: disposeBag)
        
        view.presentTrigger
            .emit(onNext: { route in
                router.transition(route)
            })
            .disposed(by: disposeBag)
    }
}
