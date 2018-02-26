import UIKit

class ContainerViewController: UIViewController {
    
    func set(_ controller: UIViewController) {
        
        childViewControllers.forEach { c in
            c.removeFromParentViewController()
            c.view.removeFromSuperview()
        }
        addChildViewController(controller)
        view.addSubview(controller.view)
        controller.didMove(toParentViewController: self)
    }
    
    override func viewDidLoad() {
        view.backgroundColor = .orange
    }
}
