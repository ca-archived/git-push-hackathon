import UIKit

class GistCell: UITableViewCell {
    @IBOutlet private weak var iconImageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var timeStampLabel: UILabel!
    @IBOutlet private weak var descriptionLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    func bind(_ viewModel: GistListViewModel) -> Self {
        iconImageView
    }
}

extension GistCell {
    private enum Const {
        static let cornerRadius: CGFloat = 25.0
    }
}
