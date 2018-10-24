import UIKit

extension UIViewController {
    static var className: String {
        return String(describing: self)
    }
    
    static var storyboardName: String {
        return className.replacingOccurrences(of: "ViewController", with: "")
    }
}
