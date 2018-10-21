import RxCocoa
import RxSwift
import UIKit

protocol SplashUseCaseProtocol {
    func hasAccessToken() -> Observable<Bool>
}

final class SplashUseCase: SplashUseCaseProtocol {
    
    private var repository: OAuthRepositoryProtocol
    
    init (repository: OAuthRepositoryProtocol) {
        self.repository = repository
    }
    
    func hasAccessToken() -> Observable<Bool> {
        return repository.hasAccessToken()
    }
}
