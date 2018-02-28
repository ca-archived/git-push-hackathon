//
//  EventListCell.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import UIKit
import Kingfisher

private let screenSize : CGSize = UIScreen.main.bounds.size
private let screenWidth : CGFloat = screenSize.width
private let screenHeight : CGFloat = screenSize.height

private let topMargin: CGFloat = 10.0
private let leftMargin: CGFloat = 10.0
private let bottomMargin: CGFloat = 20.0
private let margin10: CGFloat = 10.0
private let margin5: CGFloat = 5.0

private let iconSize = CGSize(width: 50.0, height: 50.0)
private let descriptionLabelPadding: CGFloat = 6.0
private let descriptionLabelHeight: CGFloat = 50.0
private let dateLabelPadding: CGFloat = 0.5
private let dateLabelSize = CGSize(width: 220.0, height: 13.0)


final class EventListCell: UITableViewCell {
    private var iconImageView: UIImageView!
    private var descriptionLabel: UILabel!
    private var dateLabel: UILabel!

    
    override init(style: UITableViewCellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        contentView.backgroundColor = .white
        configureViews()
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func configureViews() {
        iconImageView = UIImageView()
        iconImageView.layer.masksToBounds = true
        iconImageView.layer.cornerRadius = iconSize.width * 0.5
        iconImageView.backgroundColor = .white
        iconImageView.layer.borderColor = UIColor.black.cgColor
        contentView.addSubview(iconImageView)
        
        descriptionLabel = UILabel()
        descriptionLabel.font = UIFont.systemFont(ofSize: 17, weight: .regular)
        descriptionLabel.textColor = .black
        descriptionLabel.textAlignment = .left
        descriptionLabel.numberOfLines = 0
        contentView.addSubview(descriptionLabel)
        
        dateLabel = UILabel()
        dateLabel.font = UIFont.systemFont(ofSize: 13, weight: .regular)
        dateLabel.textColor = .gray
        dateLabel.textAlignment = .left
        contentView.addSubview(dateLabel)
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        dateLabel.frame = CGRect(x: leftMargin,
                                 y: topMargin + dateLabelPadding,
                                 width: dateLabelSize.width,
                                 height: dateLabelSize.height)
        iconImageView.frame = CGRect(x: dateLabel.frame.origin.x,
                                     y: dateLabel.frame.maxY + margin5,
                                     width: iconSize.width,
                                     height: iconSize.height)
        descriptionLabel.frame = CGRect(x: iconImageView.frame.maxX + margin10,
                                        y: iconImageView.frame.origin.y,
                                        width: screenWidth - leftMargin - iconSize.width - margin10 - descriptionLabelPadding - margin10,
                                        height: descriptionLabelHeight)
        descriptionLabel.sizeToFit()
        
    }
    
    private func getActionFromType(_ type : String) -> String {
        // TODO: eventの数え上げ
        var action : String = type
        switch action {
        case "PushEvent":
            action = "pushed to"
            break
        case "DeleteEvent":
            action = "deleted"
            break
        case "CreateEvent":
            action = "created"
            break
        case "WatchEvent":
            action = "starred"
            break
        default:
            break
        }
        return action
    }
    
    var activity: Activity? {
        didSet {
            guard let activity = activity else { return }

            iconImageView.kf.setImage(with: URL(string: activity.actor.avatarUrl))
            descriptionLabel.text = String(format:"%@ %@ %@", activity.actor.login, getActionFromType(activity.type), activity.repo.name )
            
            let formatter = DateFormatter()
            formatter.dateFormat = DateFormatter.dateFormat(fromTemplate: "ydMMMM Hms", options: 0, locale: Locale(identifier: "ja_JP"))
            dateLabel.text = formatter.string(from: activity.createdAt)
        }
    }
    
    class func height(activity: Activity) ->CGFloat {
        let cellHeight: CGFloat = topMargin + dateLabelPadding + dateLabelSize.height + margin5 + iconSize.height + bottomMargin
        return cellHeight
    }
}
