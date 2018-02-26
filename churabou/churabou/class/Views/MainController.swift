import UIKit

class MainController: UIViewController {
    
    fileprivate var container = ContainerViewController()
    fileprivate var sideMenu = SideMenuController()
    fileprivate var isSideMenuShowing = false

    override func viewDidLoad() {
        setNavigationBar()
        setContainerViewController()
        setSideMenu()
        didSelectMenu(1)
    }

    fileprivate func setNavigationBar() {

        let leftBarItem = UIBarButtonItem(title: "menu", style: .plain, target: self, action: #selector(actionMenu))
        navigationItem.leftBarButtonItem = leftBarItem
    }
    
    @objc fileprivate func actionMenu() {
        toggleSideMenu(show: !isSideMenuShowing)
    }
    
    fileprivate func toggleSideMenu(show: Bool) {
        
        let k: CGFloat = show ? -1/3 : -1
        UIView.animate(withDuration: 0.2, animations: { () -> Void in
            self.sideMenu.view.transform = CGAffineTransform(translationX: (self.view.frame.width * k), y: 0)
        })
        isSideMenuShowing = show
    }
    
    fileprivate func setContainerViewController() {
        
        addChildViewController(container)
        view.addSubview(container.view)
        container.didMove(toParentViewController: self)
    }
    
    fileprivate func setSideMenu() {

        sideMenu.delegate = self
        addChildViewController(sideMenu)
        view.addSubview(sideMenu.view)
        sideMenu.didMove(toParentViewController: self)
        sideMenu.view.transform = CGAffineTransform(translationX: (view.bounds.width * -1), y: 0)
    }
}

extension MainController: SideMenuDelegate {
    
    func didSelectMenu(_ index: Int) {

        toggleSideMenu(show: false)
        var c = UIViewController()
        switch index {
        case 0: c = ProfileController()
            title = "profile"
        case 1: c = ActivityEventController(.news)
            title = "news"
        case 2: c = ActivityFeedViewController()
            title = "timeline"
        case 3: c = ActivityEventController(.user)
            title = "event"
        case 4: c = RLController()
            title = "repository"
        case 5: c.view.backgroundColor = .black
        default: return
        }
        container.set(c)
    }
}
