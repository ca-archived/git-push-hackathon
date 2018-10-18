//
//  FollowDataTableViewCell.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/31.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class FollowDataTableViewCell: UITableViewCell {

    @IBOutlet private weak var contentName: UILabel!
    @IBOutlet private weak var contentValue: UILabel!
    
    func bind(_ cell: FollowDataTableViewCell, name: String, value: String) {
        cell.contentName.text = name
        cell.contentValue.text = value
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
}
