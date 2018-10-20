import RxCocoa
import RxSwift
import UIKit

final class SplashPresenter: SplashPresenterProtocol {
    
    private weak var view: SplashViewProtocol!
    private var interactor: SplashInteractorProtocol
    private var router: SplashRouterProtocol
    
    private let disposeBag = DisposeBag()
    
    var isLoading: Observable<Bool> {
        return _isLoading.asObservable()
    }
    private let _isLoading = PublishRelay<Bool>()
    
//    var isConnectNetWork: Observable<Bool> {
//        return _isConnectNetWork.asObservable()
//    }
//    private let _isConnectNetWork = PublishRelay<Bool>()
    
    var isAccessToken: Observable<Bool> {
        return _isAccessToken.asObservable()
    }
    private let _isAccessToken = PublishRelay<Bool>()
    
    init (view: SplashViewProtocol, interactor: SplashInteractorProtocol, router: SplashRouterProtocol) {
        
        self.view = view
        self.interactor = interactor
        self.router = router
        
        view.refreshTrigger
            .emit(onNext: { [weak self] _ in
                guard let `self` = self else { return }
                
                self._isLoading.accept(true)
                
                interactor.fetchAccessToken()
                    .subscribe(onNext: { accessToken in
                        if accessToken != nil {
                            self._isAccessToken.accept(true)
                        } else {
                            self._isAccessToken.accept(false)
                        }
                    }, onCompleted: {
                        self._isLoading.accept(false)
                    })
                    .disposed(by: self.disposeBag)

            })
            .disposed(by: disposeBag)
        
        view.presentTrigger
            .emit(onNext: { route in
                router.transition(route: route)
            })
            .disposed(by: disposeBag)
    }
}
