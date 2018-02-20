//
//  EventCardCollectionViewCell.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/08.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import Down

class EventCardCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var iconImageView: UIImageView!
    @IBOutlet weak var eventTitleLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var repositoryTitleLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var readmeView: UIView!
    @IBOutlet weak var repoInfoLabel: UILabel!
    @IBOutlet weak var readMeLabel: UILabel!

    var markDownString: String? {

        didSet {

            guard let str = markDownString,
                let downView = try? DownView(frame: readmeView.bounds, markdownString: str) else {

                return
            }
            readmeView.addSubview(downView)
        }
    }
}

extension EventCardCollectionViewCell: Reusable, NibLoadable {}
