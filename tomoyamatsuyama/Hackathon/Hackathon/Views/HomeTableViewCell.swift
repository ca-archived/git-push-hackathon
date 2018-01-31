//
//  HomeTableViewCell.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class HomeTableViewCell: UITableViewCell {
    
    @IBOutlet weak var avatarView: UIImageView!
    @IBOutlet weak var titleLabel: UILabel!
    
    private func imageSet(avatarUrl: String) -> UIImage? {
        avatarView.layer.cornerRadius = avatarView.frame.size.width * 0.5
        avatarView.layer.masksToBounds = true
        guard let imageUrl = URL(string: avatarUrl) else { return nil }
        do {
            let imageData = try Data(contentsOf: imageUrl, options: Data.ReadingOptions.mappedIfSafe)
            guard let image = UIImage(data: imageData) else { return nil }
            return image
        } catch {
            print("Error: cant create image.")
            return nil
        }
    }
    
    enum type: String {
        case ForkEvent = "ForkEvent"
        case PublicEvent = "PublicEvent"
        case WatchEvent = "WatchEvent"
        case CreateEvent = "CreateEvent"
    }
    
    func bind(_ cell: HomeTableViewCell, event: Events) -> HomeTableViewCell {
        cell.avatarView.image = imageSet(avatarUrl: event.actor.avatar_url)
        
        switch event.type {
        case type.ForkEvent.rawValue :
            cell.titleLabel.text = "\(event.actor.display_login) forked \(event.repo.name)"
        case type.PublicEvent.rawValue :
            cell.titleLabel.text = "\(event.actor.display_login) has open sourced \(event.repo.name)"
        case type.WatchEvent.rawValue :
            cell.titleLabel.text = "\(event.actor.display_login) starred \(event.repo.name)"
        case type.CreateEvent.rawValue :
            cell.titleLabel.text = "\(event.actor.display_login) create repository \(event.repo.name)"
        default:
            break
        }
        
        cell.layoutMargins = UIEdgeInsetsMake(0.0, avatarView.frame.size.width + 18.0, 0.0, 0.0)
        return cell
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }

}
