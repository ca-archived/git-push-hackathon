//
//  FollowDataTableViewCell.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/31.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class FollowDataTableViewCell: UITableViewCell {

    @IBOutlet weak var contentName: UILabel!
    @IBOutlet weak var contentValue: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

}
