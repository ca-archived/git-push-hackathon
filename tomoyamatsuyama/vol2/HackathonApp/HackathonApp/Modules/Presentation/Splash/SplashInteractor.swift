import RxCocoa
import RxSwift

final class SplashInteractor: SplashInteractorProtocol {
    private let useCase: SplashUseCaseProtocol
    
    init(useCase: SplashUseCaseProtocol) {
        self.useCase = useCase
    }
    
    func hasAccessToken() -> Observable<Bool> {
        return useCase.hasAccessToken()
    }
}
