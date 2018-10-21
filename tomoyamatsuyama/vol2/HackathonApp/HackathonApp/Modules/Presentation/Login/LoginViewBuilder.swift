import Alamofire
import RxCocoa
import RxSwift
import UIKit

struct LoginViewBuilder {
    static func build() -> LoginViewController {
        let viewController: LoginViewController = .instantiate()
        
        // Data
        let localDataStore = OAuthLocalDataStore()
        let remoteDataStore = OAuthRemoteDataStore()
        
        // Domain
        let repository = OAuthRepository(local: localDataStore, remote: remoteDataStore)
        let useCase = LoginUseCase(repository: repository)
        
        // Presentation: VIPER
        let interactor = LoginInteractor(useCase: useCase)
        let router = LoginRouter(view: viewController)
        let presenter = LoginPresenter(view: viewController, interactor: interactor, router: router)
        
        // DI
        viewController.inject(with: presenter)
        
        return viewController
    }
}
