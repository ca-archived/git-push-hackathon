//
//  EventCardTableViewCell.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/20.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift
import Down

// MARK: - EventCardTableViewCell

class EventCardTableViewCell: UITableViewCell {

    // MARK: Internal

    @IBOutlet weak var eventLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var iconImageView: UIImageView!
    @IBOutlet weak var repoNameLabel: UILabel!
    @IBOutlet weak var repoDescLabel: UILabel!
    @IBOutlet weak var repoInfoLabel: UILabel!
    @IBOutlet weak var readmeView: UIView!
    @IBOutlet weak var readmeConstraint: NSLayoutConstraint!
    @IBOutlet weak var readmeButton: UIButton!

    var readmeUrl: URL? {

        didSet {

            if readmeUrl == nil {

                readmeView.isHidden = true
            } else {

                readmeView.isHidden = false
            }
        }
    }
    var downView: DownView?
    var completion: (() -> Void)!
    var isShowReadme: Bool! {

        didSet {

            if !isShowReadme {

                readmeConstraint.constant = 22
                downView?.removeFromSuperview()
                readmeButton.setImage(#imageLiteral(resourceName: "open_readme"), for: .normal)
                readmeButton.imageView?.contentMode = .scaleAspectFill
            } else if let url = self.readmeUrl {

                readmeConstraint.constant = 200
                let rect = self.readmeView.bounds
                if let downView = try? DownView(frame: CGRect(origin: rect.origin, size: CGSize(width: UIScreen.main.bounds.width - 40, height: 200)), markdownString: String(contentsOf: url, encoding: .utf8)) {

                    readmeView.insertSubview(downView, belowSubview: self.readmeButton)
                    self.downView = downView
                    readmeButton.setImage(#imageLiteral(resourceName: "close_readme"), for: .normal)
                    readmeButton.imageView?.contentMode = .scaleAspectFill
                }
            }
        }
    }

    // MARK: UITableViewCell

    override func awakeFromNib() {

        super.awakeFromNib()
    }

    // MARK: Private

    @IBAction func tapReadMe(_ sender: Any) {

        completion()
    }
}

// MARK: Reusable and NibLoadable

extension EventCardTableViewCell: Reusable, NibLoadable {}
