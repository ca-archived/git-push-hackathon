import RxCocoa
import RxSwift
import UIKit

protocol SplashUseCaseProtocol {
    func fetchAccessToken() -> Observable<String?>
}

final class SplashUseCase: SplashUseCaseProtocol {
    
    private var repository: SplashRepositoryProtocol
    
    init (repository: SplashRepositoryProtocol) {
        self.repository = repository
    }
    
    func fetchAccessToken() -> Observable<String?> {
        return repository.fetchAccessToken()
    }
}
