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
    
    enum EventType: String {
        case ForkEvent = "ForkEvent"
        case PublicEvent = "PublicEvent"
        case WatchEvent = "WatchEvent"
        case CreateEvent = "CreateEvent"
        case MemberEvent = "MemberEvent"
        case IssueCommentEvent = "IssueCommentEvent"
    }
    
    func bind(_ cell: HomeTableViewCell, event: Events) {
        let resourse = URL(string: event.actor.avatar_url)
        cell.avatarView.kf.setImage(with: resourse)
        
        //TODO: eventはまだある。
        switch event.type {
        case EventType.ForkEvent.rawValue :
            if let payload = event.payload, let forkee = payload.forkee, let fullName = forkee.full_name {
                cell.titleLabel.text = "\(event.actor.display_login) forked \(event.repo.name) to \(fullName)"
            }
        case EventType.PublicEvent.rawValue:
            cell.titleLabel.text = "\(event.actor.display_login) has open sourced \(event.repo.name)"
        case EventType.WatchEvent.rawValue:
            cell.titleLabel.text = "\(event.actor.display_login) starred \(event.repo.name)"
        case EventType.CreateEvent.rawValue:
            cell.titleLabel.text = "\(event.actor.display_login) created a repository \(event.repo.name)"
        case EventType.MemberEvent.rawValue:
            cell.titleLabel.text = "\(event.actor.display_login) added \(event.payload!.member!.login) as a collaborator to \(event.repo.name)"
        case EventType.IssueCommentEvent.rawValue:
            cell.titleLabel.text = "\(event.actor.display_login) comment on issue \(event.repo.name)"
        default:
            break
        }
        cell.layoutMargins = UIEdgeInsetsMake(0.0, avatarView.frame.size.width + 18.0, 0.0, 0.0)
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

