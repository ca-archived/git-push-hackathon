import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistCreatePresenter: GistCreatePresenterProtocol {
    private weak var view: GistCreateViewProtocol!
    private var router: GistCreateRouterProtocol
    
    private let disposeBag = DisposeBag()
    
    let isLoading: Observable<Bool>
    private let _isLoading = PublishRelay<Bool>()
    
    init(view: GistCreateViewProtocol,
         router: GistCreateRouterProtocol) {
        
        self.view = view
        self.router = router
        
        self.isLoading = _isLoading.asObservable()
        
        view.presentTrigger
            .emit(onNext: { route in
                router.transition(route)
            })
            .disposed(by: disposeBag)
    }
}
