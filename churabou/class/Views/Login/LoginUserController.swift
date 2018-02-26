import UIKit

class LoginUserController: UIViewController {
    
    fileprivate var imageView = UIImageView()
    fileprivate var nameLabel = UILabel()
    
    fileprivate func initializeView() {
        
        view.backgroundColor = UIColor(white: 0.8, alpha: 1/0)
        
        let size: CGFloat = 150
        imageView.frame.size = CGSize(width: size, height: size)
        imageView.layer.cornerRadius = size / 2
        imageView.clipsToBounds = true
        imageView.center = view.center
        view.addSubview(imageView)
        
        nameLabel.frame.size = CGSize(width: 200, height: 50)
        nameLabel.center = view.center
        nameLabel.frame.origin.y += size
        nameLabel.textAlignment = .center
        view.addSubview(nameLabel)
    }
    
    override func viewDidLoad() {
        initializeView()
        feachUser()
    }
    
    
    func feachUser() {
        
        let reqest = GetUserRequest()
        GithubSession.send(request: reqest, completion: { response in
            
            switch response {
            case .success(_, let user):
                self.update(user)
            case .failure:
                return
            }
        })
    }
    
    func update(_ user: User) {
        nameLabel.text = "logging in as \(user.name)"
        imageView.loadImage(urlString: user.iconURL)
        Config.login = user.name
        present()
    }
    
    fileprivate func present()  {
        let c = MainController()
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.0, execute: {
            self.navigationController?.pushViewController(c, animated: true)
        })
    }
}

