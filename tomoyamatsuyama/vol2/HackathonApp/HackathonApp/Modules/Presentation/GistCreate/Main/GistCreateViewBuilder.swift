import UIKit

struct GistCreateViewBuilder {
    static func build() -> UINavigationController {
        let navigationController = GistCreateViewController.instantiateNavigationController()
        
        guard let viewController = navigationController.viewControllers.first as? GistCreateViewController else { return navigationController }
        
        let router = GistCreateRouter(view: viewController)
        let presenter = GistCreatePresenter(view: viewController, router: router)
        
        // DI
        viewController.inject(presenter)
        
        return navigationController
    }
}
