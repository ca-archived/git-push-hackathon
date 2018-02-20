//
//  EventCardCollectionViewCell.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/08.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

class EventCardCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var iconImageView: UIImageView!
    @IBOutlet weak var eventTitleLabel: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var repositoryTitleLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    @IBOutlet weak var readmeView: UIView!
    @IBOutlet weak var repoInfoLabel: UILabel!
}

extension EventCardCollectionViewCell: Reusable, NibLoadable {}
