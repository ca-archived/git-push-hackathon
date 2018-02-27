//
//  UserInfoTableViewCell.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/27.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - UserInfoTableViewCell

class UserInfoTableViewCell: UITableViewCell {

    // MARK: Internal
    @IBOutlet weak var iconImageView: UIImageView!
    @IBOutlet weak var userNameLabel: UILabel!

    // MARK: UITableViewCell

    override func awakeFromNib() {

        super.awakeFromNib()
    }
}

// MARK: Reusable and NibLoadable

extension UserInfoTableViewCell: Reusable, NibLoadable {}
