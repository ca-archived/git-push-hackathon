import UIKit


protocol StoryboardInstantiable {
    
    static var storyboardName: String { get }
}


extension StoryboardInstantiable where Self: UIViewController {
    
    static func instantiateFromStoryboard() -> Self {
        
        let storyboard = UIStoryboard(name: storyboardName, bundle: nil)
        guard let controller = storyboard.instantiateInitialViewController() as? Self else {
            fatalError("生成したいViewControllerと同じ名前のStorybaordが見つからないか、Initial ViewControllerに設定されていない可能性があります。")
        }
        return controller
    }
}
