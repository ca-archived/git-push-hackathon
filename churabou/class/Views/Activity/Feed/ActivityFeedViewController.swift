import UIKit


class ActivityFeedViewController: UIViewController {
    
    let viewModel = ActivityFeedViewModel()
    let baseView = ActivityFeedView()
    
    override func viewDidLoad() {
        baseView.viewModel = viewModel
        baseView.frame = view.frame
        view.addSubview(baseView)
        bindToViewModel()
        viewModel.fetchFeeds()
        hud.setUp(view)
    }
    
    let hud = HUDView()
    fileprivate func bindToViewModel() {
        
        viewModel.isLoadingDidSet = { (isloading) in
            if isloading {
                self.hud.show()
            } else {
                DispatchQueue.main.asyncAfter(deadline: .now()+0.3, execute: {
                    self.hud.hide()
                })
                
            }
        }
        
        viewModel.activitiesDidSet = { _ in
            self.baseView.update()
        }
        
        viewModel.errorDidSet = { (error) in
            ErrorAlert.show(self)
        }
        
        baseView.reachedBottomDidSet = { (reachedBottom) in
            self.viewModel.loadMore()
        }
    }
}
