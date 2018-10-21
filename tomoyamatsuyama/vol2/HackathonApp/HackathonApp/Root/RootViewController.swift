import UIKit

final class RootViewController: UIViewController, StoryboardInstantiatable {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        showSplash()
    }
    
    private func showSplash() {
        let splashViewController = SplashViewBuilder.build()
        
        UserDefaults.standard.removeObject(forKey: OAuth.accessToken.key)
        navigationController?.pushViewController(splashViewController, animated: false)
    }
}


extension RootViewController {
    private enum Const {
        static let title = "Root"
    }
}
