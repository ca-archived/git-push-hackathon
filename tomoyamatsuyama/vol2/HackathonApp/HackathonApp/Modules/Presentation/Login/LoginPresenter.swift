import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class LoginPresenter: LoginPresenterProtocol {
    private let disposeBag = DisposeBag()
    
    private weak var view: LoginViewProtocol!
    private let interactor: LoginInteractorProtocol
    private let router: LoginRouterProtocol
    
    let webViewURL: Observable<String>
    private let _webViewURL = PublishRelay<String>()
    
    var status: Observable<LoginPresenter.Status>
    private let _status = PublishRelay<LoginPresenter.Status>()
    
    private func convertToCode(from request: URLRequest) -> String {
        guard let url = request.url else { fatalError("TODO") }
        guard let components = URLComponents(url: url, resolvingAgainstBaseURL: false) else { fatalError("TODO") }
        guard let items = components.queryItems else { fatalError("TODO") }
        
        let code = items
            .filter { $0.name == "code" }
            .compactMap { $0.value }
            .first ?? ""
        
        return code
    }
    
    init(view: LoginViewProtocol, interactor: LoginInteractorProtocol, router: LoginRouterProtocol) {
        
        self.view = view
        self.interactor = interactor
        self.router = router
        
        self.webViewURL = _webViewURL.asObservable()
        self.status = _status.asObservable()
        
        view.redirectTrigger
            .emit(onNext: { [weak self] request in
                guard let `self` = self else { return }
                self._status.accept(.loading)
                
                let code = self.convertToCode(from: request)
                
                interactor.fetchAccessToken(with: code)
                    .flatMap { authorization -> Observable<LoginPresenter.Status> in
                        
                        UserDefaults.standard.set(authorization.accessToken, forKey: OAuth.accessToken.key)
                        
                        return .just(.completed)
                    }
                    .bind(to: self._status)
                    .disposed(by: self.disposeBag)
            })
            .disposed(by: disposeBag)
        
        view.transition
            .emit(onNext: { route in
                router.transition(route: route)
            })
            .disposed(by: disposeBag)
    }
}

extension LoginPresenter {
    enum Status {
        case loading
        case error(Error)
        case completed
    }
}
