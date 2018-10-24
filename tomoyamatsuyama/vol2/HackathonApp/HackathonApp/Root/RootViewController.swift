import UIKit

// リファクタリング対象

final class RootViewController: UIViewController, StoryboardInstantiatable {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        showSplash()
    }
    
    private func showSplash() {
        let splashViewController = SplashViewBuilder.build()
        
        navigationController?.present(splashViewController, animated: true, completion: nil)
    }
}
