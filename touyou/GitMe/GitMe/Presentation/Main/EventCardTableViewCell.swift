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

    var readmeUrl: URL?
    var downView: DownView?
    var completion: (() -> Void)!

    // MARK: UITableViewCell

    override func awakeFromNib() {

        super.awakeFromNib()
    }

    // MARK: Private

    @IBAction func tapReadMe(_ sender: Any) {

        print("tap the button \(self.isShowReadme) \(self.readmeUrl)")
        if isShowReadme {

            readmeConstraint.constant = 20
            downView?.removeFromSuperview()
            readmeButton.setTitle("▼READMEをみる", for: .normal)
            readmeButton.setTitleColor(.white, for: .normal)
            isShowReadme = false
        } else if let url = self.readmeUrl {

            readmeConstraint.constant = 100
            let rect = self.readmeView.bounds
            if let downView = try? DownView(frame: CGRect(origin: rect.origin, size: CGSize(width: rect.width, height: 100)), markdownString: String(contentsOf: url, encoding: .utf8)) {

                readmeView.insertSubview(downView, belowSubview: self.readmeButton)
                self.downView = downView
                readmeButton.setTitle("▲READMEを閉じる", for: .normal)
                readmeButton.setTitleColor(UIColor.GitMe.darkGray, for: .normal)
                isShowReadme = true
            }
        }
        completion()
    }
    private var isShowReadme: Bool = false
}

extension EventCardTableViewCell: Reusable, NibLoadable {}
