import UIKit

struct RootViewBuilder {
    static func build() -> UINavigationController {
        let viewController: RootViewController = .instantiate()
        let navigationController = UINavigationController(rootViewController: viewController)
        
        navigationController.navigationBar.titleTextAttributes = [.foregroundColor : Color.Font.white]
        navigationController.navigationBar.barTintColor = Color.Background.black
        return navigationController
    }
}
