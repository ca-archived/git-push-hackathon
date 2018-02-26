import UIKit

class ProfileController: UIViewController {
    
    fileprivate var topWrpperView = UIView()
    fileprivate var imageView = UIImageView()
    fileprivate var nameLabel = UILabel()
    fileprivate let viewModel = ProfileViewModel()
    
    fileprivate func initializeView() {
        
        view.backgroundColor = .white
        topWrpperView.backgroundColor = UIColor(white: 0.2, alpha: 0.5)
        topWrpperView.frame = CGRect(x: 0, y: 64, width: view.frame.width, height: 200)
        view.addSubview(topWrpperView)
        let size: CGFloat = 100
        imageView.frame.origin = CGPoint(x: (view.frame.width-size)/2, y: 50)
        imageView.frame.size = CGSize(width: size, height: size)
        imageView.layer.cornerRadius = size / 2
        imageView.clipsToBounds = true
        topWrpperView.addSubview(imageView)
        
        nameLabel.frame.origin = CGPoint(x: (view.frame.width-size)/2, y: 160)
        nameLabel.frame.size = CGSize(width: 100, height: 20)
        nameLabel.textAlignment = .center
        nameLabel.font = UIFont.boldSystemFont(ofSize: 13)
        nameLabel.textColor = .white
        topWrpperView.addSubview(nameLabel)
    }
    
    override func viewDidLoad() {
        initializeView()
        bindToViewModel()
        viewModel.fetchUser()
    }
    
    
    fileprivate func bindToViewModel() {
        
        viewModel.userDidSet = { user in
            
            self.nameLabel.text = user.name
            self.imageView.loadImage(urlString: user.iconURL)
        }
    }
}

