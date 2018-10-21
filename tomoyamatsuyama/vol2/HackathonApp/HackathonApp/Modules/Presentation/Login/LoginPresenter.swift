import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class LoginPresenter: LoginPresenterProtocol {
    private let disposeBag = DisposeBag()
    
    var webViewURL: Observable<String> {
        return _webViewURL.asObservable()
    }
    private let _webViewURL = PublishRelay<String>()
    
    var status: Observable<LoginPresenter.Status> {
        return _status.asObservable()
    }
    private let _status = PublishRelay<LoginPresenter.Status>()
    
    private weak var view: LoginViewProtocol!
    private let interactor: LoginInteractorProtocol
    private let router: LoginRouterProtocol
    
    private func convertToCode(from request: URLRequest) -> String {
        guard let url = request.url else {
            fatalError("TODO")
        }
        guard let components = URLComponents(url: url, resolvingAgainstBaseURL: false) else {
            fatalError("TODO")
        }
        guard let items = components.queryItems else {
            fatalError("TODO")
        }
        
        let code = items
            .filter { $0.name == "code" }
            .compactMap { $0.value }
            .first!
        
        return code
    }
    
    init(view: LoginViewProtocol, interactor: LoginInteractorProtocol, router: LoginRouterProtocol) {
        
        self.view = view
        self.interactor = interactor
        self.router = router
        
        view.redirectTrigger
            .emit(onNext: { [weak self] request in
                guard let `self` = self else { return }
                self._status.accept(.loading)
                
                // RedirectのURLからcodeの作成
                
                let code = self.convertToCode(from: request)
                
                interactor.fetchAccessToken(with: code)
                    .flatMap { authorization -> Observable<LoginPresenter.Status> in
                        
                        UserDefaults.standard.set(OAuth.accessToken.key, forKey: authorization.accessToken)
                        
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
