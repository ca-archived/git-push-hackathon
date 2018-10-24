import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistPostPresenter: GistPostPresenterProtocol {

    private weak var view: GistPostViewProtocol!
    private var interactor: GistPostInteractorProtocol
    private var router: GistPostRouterProtocol
    
//    let isLoading: Observable<Bool>
//    private let _isLoading = PublishRelay<Bool>()
    
    var viewModel: GistCreateModel
    
    private let disposeBag = DisposeBag()
    
    init(view: GistPostViewProtocol, interactor: GistPostInteractorProtocol, router: GistPostRouterProtocol, viewModel: GistCreateModel) {
        self.view = view
        self.interactor = interactor
        self.router = router
        
//        self.isLoading = _isLoading.asObservable()
        self.viewModel = viewModel
        
        view.updateSwitchTrigger
            .emit(onNext: { [weak self] isOn in
                guard let `self` = self else { return }
                self.viewModel.isPublic = isOn
            })
            .disposed(by: disposeBag)
        
        view.createTrigger
            .emit(onNext: { [weak self] _ in
                guard let `self` = self else { return }
                
                interactor.post(self.viewModel)
                    .subscribe({ _ in
                        router.transition(.gistList)
                    })
                    .disposed(by: self.disposeBag)
            })
            .disposed(by: disposeBag)
        
        view.presentTrigger
            .emit(onNext: { route in
                router.transition(route)
            })
            .disposed(by: disposeBag)
        
    }
}
