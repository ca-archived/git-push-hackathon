
import UIKit
import Nuke


final class LoginUserPopUp: BaseView {
    
    private var imageView = UIImageView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.clipsToBounds = true
    }
    
    private var label = UILabel().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.textColor = .white
        it.font = .systemFont(ofSize: 12)
        it.textAlignment = .center
    }
    
    private (set) var doneButton = UIButton().apply { it in
        it.backgroundColor = .orange
        it.layer.cornerRadius = 6
        it.setTitle("ok", for: .normal)
        it.setTitleColor(.white, for: .normal)
    }
    
    override func initializeView() {
        transform = CGAffineTransform.identity.scaledBy(x: 0.9, y: 0.9)
        alpha = 0
        backgroundColor = .lightGray
        layer.cornerRadius = 6
        addSubview(imageView)
        addSubview(label)
        addSubview(doneButton)
    }
    
    override func initializeConstraints() {
        
        imageView.snp.makeConstraints { (make) in
            make.top.left.right.equalToSuperview()
            make.height.equalTo(imageView.snp.width)
        }
        
        label.snp.makeConstraints { (make) in
            make.bottom.equalTo(doneButton.snp.top).offset(-10)
            make.left.equalTo(20)
            make.right.equalTo(-20)
            make.height.equalTo(20)
        }
        
        doneButton.snp.makeConstraints { (make) in
            make.width.equalTo(200)
            make.centerX.equalToSuperview()
            make.bottom.equalTo(-20)
            make.height.equalTo(40)
        }
    }
    
    func show(user: User) {
        Nuke.loadImage(with: user.iconURL!, into: imageView)
        doneButton.setTitle("start", for: .normal)
        label.text = "hi! \(user.name)"
        
        UIView.animate(withDuration: 0.25) {
            self.alpha = 1
            self.transform = .identity
        }
    }
}
