import UIKit

struct GistCreateViewBuilder {
    static func build() -> UINavigationController {
        let navigationController = GistCreateViewController.instantiateNavigationController()
        
        guard let viewController = navigationController.viewControllers.first as? GistCreateViewController else { return navigationController }
        
        // Data
        let localDataStore = GistLocalDataStore()
        let remoteDataStore = GistRemoteDataStore()
        
        // Domain
        let repository = GistRepository(local: localDataStore, remote: remoteDataStore)
        let useCase = GistCreateUseCase(repository: repository)
        
        // Presentation: VIPER
        let interactor = GistCreateInteractor(useCase: useCase)
        let router = GistCreateRouter(view: viewController)
        let presenter = GistCreatePresenter(view: viewController, interactor: interactor, router: router)
        
        // DI
        viewController.inject(presenter)
        
        return navigationController
    }
}
