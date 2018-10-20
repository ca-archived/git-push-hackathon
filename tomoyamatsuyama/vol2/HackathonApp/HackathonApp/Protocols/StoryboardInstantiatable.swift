import UIKit

protocol StoryboardInstantiatable {
    static func instantiate() -> Self
}

extension StoryboardInstantiatable where Self: UIViewController {
    static func instantiate() -> Self {
        let bundle = Bundle(for: Self.self)
        let storyboard = UIStoryboard(name: storyboardName, bundle: bundle)
        let viewController = storyboard.instantiateInitialViewController()
        return viewController as! Self
    }
}
