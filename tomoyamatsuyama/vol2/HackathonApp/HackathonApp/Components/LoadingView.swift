import UIKit
import Foundation

final class LoadingView: UIView {
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        initialize()
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        initialize()
    }
    
    private let indicator = UIActivityIndicatorView(activityIndicatorStyle: .whiteLarge)
    
    func initialize() {
        indicator.frame = CGRect(x: 0, y: 0, width: 80, height: 80)
        indicator.layer.cornerRadius = 8
        indicator.activityIndicatorViewStyle = .whiteLarge
        indicator.center = self.center
        indicator.backgroundColor = UIColor(red: 0.259, green: 0.259, blue: 0.259, alpha: 0.8)
        indicator.hidesWhenStopped = true
        
        addSubview(indicator)
        isHidden = true
    }
    
    override var isHidden: Bool {
        get {
            return super.isHidden
        }
        set {
            super.isHidden = newValue
            
            if newValue {
                indicator.stopAnimating()
            } else {
                indicator.startAnimating()
            }
        }
    }
    
}

