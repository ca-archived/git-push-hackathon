import UIKit

extension UITableView {
    
    func dequeueReusableCell<C: UITableViewCell>() -> C {
        guard let cell = dequeueReusableCell(withIdentifier: String(describing: C.self)) as? C else {
            fatalError("invalid cell type")
        }
        return cell
    }
}
