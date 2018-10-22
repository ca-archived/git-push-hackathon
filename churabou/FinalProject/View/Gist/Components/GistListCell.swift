//
//  GistListCell.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/16.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import Nuke

final class GistListCell: BaseTableViewCell {
    
    static let height: CGFloat = 100
    
    private var iconImageView = UIImageView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
    }
    
    private var titleLabel = UILabel().apply { it in
        it.textAlignment = .left
        it.textColor = .darkGray
        it.font = .systemFont(ofSize: 13)
    }
    
    override func initializeView() {
        contentView.addSubview(iconImageView)
        contentView.addSubview(titleLabel)
    }
    
    override func initializeConstraints() {
        
        iconImageView.snp.makeConstraints { make in
            make.size.equalTo(40)
            make.top.left.equalTo(5)
        }
        
        titleLabel.snp.makeConstraints { make in
            make.left.equalTo(iconImageView.snp.right).offset(10)
            make.height.equalTo(40)
            make.right.equalToSuperview()
            make.top.equalTo(5)
        }
    }
    
    func configure(gist: Gist) {
        Nuke.loadImage(with: gist.user.iconURL!, into: iconImageView)
        titleLabel.text = "\(gist.user.name) / \(gist.files.first!.filename)"
    }
}
