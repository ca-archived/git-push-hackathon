import Alamofire
import RxCocoa
import RxSwift
import UIKit

struct GistListViewBuilder {
    static func build() -> GistListViewController {
        
        let viewController: GistListViewController = .instantiate()
        
        // Data
        let localDataStore = GistLocalDataStore()
        let remoteDataStore = GistRemoteDataStore()
        
        // Domain
        let repository = GistRepository(local: localDataStore, remote: remoteDataStore)
        let useCase = GistListUseCase(repository: repository)
        
        // Presentation: VIPER
        let interactor = GistListInteractor(useCase: useCase)
        let router = GistListRouter(view: viewController)
        let presenter = GistListPresenter(view: viewController, interactor: interactor, router: router)
        
        // DI
        viewController.inject(presenter)
        
        return viewController
    }
}
