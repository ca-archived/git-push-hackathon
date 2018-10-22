//
//  FollowerListCell.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/20.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//


import SnapKit
import Nuke

final class FollowerCell: BaseTableViewCell {
    
    static let height: CGFloat = 40
    
    func configure(user: User) {
        label.text = user.name
        if let url = user.iconURL {
            Nuke.loadImage(with: url,
                           options: ImageLoadingOptions(
                            transition: .fadeIn(duration: 0.33)),
                           into: iconImageView)
        }
    }
    
    private var iconImageView = UIImageView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.clipsToBounds = true
        it.layer.cornerRadius = 15
    }
    
    private var label = UILabel().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.textAlignment = .left
        it.textColor = .black
        it.textAlignment = .left
    }
    
    override func initializeView() {
        addSubview(iconImageView)
        addSubview(label)
    }
    
    override func initializeConstraints() {
        
        iconImageView.snp.makeConstraints { make in
            make.size.equalTo(30)
            make.left.equalTo(10)
            make.centerY.equalToSuperview()
        }
        
        label.snp.makeConstraints { make in
            make.right.equalTo(-20)
            make.height.equalTo(30)
            make.left.equalTo(iconImageView.snp.right).offset(10)
            make.centerY.equalToSuperview()
        }
    }
}
