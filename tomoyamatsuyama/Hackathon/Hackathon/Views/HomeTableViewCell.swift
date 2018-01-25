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
    
    func bind(_ cell: HomeTableViewCell, avatarUrl: String, title: String) -> HomeTableViewCell {
        cell.avatarView.image = imageSet(avatarUrl: avatarUrl)
            cell.titleLabel.text = title
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
