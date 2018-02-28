import UIKit

class RLController: UIViewController {
    
    let viewModel = RLViewModel()
    let baseView = RLView()
    let hud = HUDView()
    
    override func viewDidLoad() {
        baseView.viewModel = viewModel
        baseView.delegate = self
        baseView.frame = view.frame
        view.addSubview(baseView)
        hud.setUp(view)
        bindToViewModel()
        viewModel.fetchRepository()
    }
    
    
    fileprivate func bindToViewModel() {
        
        viewModel.isLoadingDidSet = { (isloading) in
            if isloading {
                self.hud.show()
            } else {
                self.hud.hide()
            }
        }
        
        viewModel.repositoriesDidSet = { _ in
            self.baseView.update()
        }
        
        viewModel.errorDidSet = { (error) in
            print(error)
        }
    }
}

extension RLController: RLViewDelegate {
    
    func didSelectCell(_ repository: Repository) {
        let c = UIViewController()
        c.title = repository.name
        navigationController?.pushViewController(c, animated: true)
    }
}
