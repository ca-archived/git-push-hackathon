import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class LoginInteractor: LoginInteractorProtocol {
    private var useCase: LoginUseCaseProtocol
    
    init(useCase: LoginUseCaseProtocol) {
        self.useCase = useCase
    }

    func fetchAccessToken(with code: String) -> Observable<Authorization> {
        return useCase.fetchAccessToken(with: code)
    }
}
