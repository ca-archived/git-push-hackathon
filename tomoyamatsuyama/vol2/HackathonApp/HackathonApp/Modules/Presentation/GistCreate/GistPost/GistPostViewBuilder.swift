import Alamofire
import RxCocoa
import RxSwift
import UIKit

struct GistPostViewBuilder {
    static func build(with viewModel: GistCreateModel) -> GistPostViewController {
        
        let viewController: GistPostViewController = .instantiate()
        
        // Data
        let localDataStore = GistLocalDataStore()
        let remoteDataStore = GistRemoteDataStore()
        
        // Domain
        let repository = GistRepository(local: localDataStore, remote: remoteDataStore)
        let useCase = GistPostUseCase(repository: repository)
        
        
        // Presentation: VIPER
        let interactor = GistPostInteractor(useCase: useCase)
        let router = GistPostRouter(view: viewController)
        let presenter = GistPostPresenter(view: viewController, interactor: interactor, router: router, viewModel: viewModel)
        
        // DI
        viewController.inject(presenter)
        
        return viewController
    }
}

