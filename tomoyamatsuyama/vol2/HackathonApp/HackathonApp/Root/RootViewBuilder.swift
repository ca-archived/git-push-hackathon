import UIKit

struct RootViewBuilder {
    static func build() -> UINavigationController {
        let viewController = RootViewController.instantiateNavigationController()
        
        guard let firstViewController = viewController.viewControllers.first as? RootViewController else { fatalError("todo") }
        
        return viewController
    }
}
