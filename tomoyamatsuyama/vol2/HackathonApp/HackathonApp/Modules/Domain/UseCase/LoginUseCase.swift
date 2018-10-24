import RxCocoa
import RxSwift
import UIKit

protocol LoginUseCaseProtocol {
    func fetchAccessToken(with code: String) -> Observable<Authorization>
}

final class LoginUseCase: LoginUseCaseProtocol {
    private var repository: OAuthRepositoryProtocol
    
    init(repository: OAuthRepositoryProtocol) {
        self.repository = repository
    }
    
    func fetchAccessToken(with code: String) -> Observable<Authorization> {
        return repository.fetchAccessToken(with: code)
    }
}
