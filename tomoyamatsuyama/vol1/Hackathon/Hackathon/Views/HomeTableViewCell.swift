//
//  HomeTableViewCell.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class HomeTableViewCell: UITableViewCell {
    
    @IBOutlet private weak var avatarView: UIImageView!
    @IBOutlet private weak var titleLabel: UILabel!
    
    func bind(_ cell: HomeTableViewCell, event: Event) {
        let resourse = URL(string: event.actor.avatar_url)
        cell.avatarView.kf.setImage(with: resourse)
        cell.titleLabel.text = EventType.discription(for: event)
        cell.layoutMargins = .init(top: 0.0, left: avatarView.frame.size.width + 18.0, bottom: 0.0, right: 0.0)
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        avatarView.layer.cornerRadius = avatarView.frame.size.width * 0.5
        avatarView.layer.masksToBounds = true
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
}

