import UIKit


class RepositoryTableViewCell: UITableViewCell, NibLoadable, Reusable {
    
    static let defaultHeight: CGFloat = 80

    @IBOutlet weak var iconView: UIImageView! {
        didSet {
            iconView.layer.cornerRadius = 50/2
            iconView.clipsToBounds = true
        }
    }
    
    @IBOutlet weak var activityLabel: UILabel! {
        didSet {
            activityLabel.numberOfLines = 0
            activityLabel.font = UIFont.systemFont(ofSize: 12)
        }
    }
    
    @IBOutlet weak var timeLabel: UILabel! {
        didSet {
            timeLabel.textColor = .darkGray
        }
    }
    
    func update(repository: Repository) {
        activityLabel.text = repository.name
    }
}
