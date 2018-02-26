import UIKit


class ActivityTableViewCell: UITableViewCell, NibLoadable, Reusable {
    
    static let defaultHeight: CGFloat = 90

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
    
    func setActivity(feed: Entry) {
        iconView.loadImage(urlString: feed.icon)
        activityLabel.text = feed.title
        timeLabel.text = "★ time ago"
    }
    
    func setActivity(event: Event) {
        iconView.loadImage(urlString: event.user.iconURL)
        activityLabel.text = event.displayedString
        timeLabel.text = "★  \(event.timeago)"
    }
}
