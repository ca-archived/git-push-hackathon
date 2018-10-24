import UIKit
import Kingfisher

class GistCell: UITableViewCell {
    @IBOutlet private weak var iconImageView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var timeStampLabel: UILabel!
    @IBOutlet private weak var descriptionLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        iconImageView.layer.cornerRadius = iconImageView.bounds.size.width * 0.5
        iconImageView.layer.masksToBounds = true
    }
    
    func configure(_ viewModel: GistListViewModel.Gist) {
        iconImageView.kf.setImage(with: viewModel.userIcon)
        titleLabel.text = viewModel.title
        timeStampLabel.text = viewModel.createdAt
        descriptionLabel.text = viewModel.description
    }
}

extension GistCell {
    private enum Const {
        static let cornerRadius: CGFloat = 30.0
    }
}
