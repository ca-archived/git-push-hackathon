//
//  GistTableViewCell.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/16.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//


import UIKit
import Nuke

final class GistTableViewCell: BaseTableViewCell {
    
    static let height: CGFloat = 120
    
    private let imageSize: CGFloat = 70
    
    private lazy var iconImageView = UIImageView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.layer.cornerRadius = imageSize / 2
        it.clipsToBounds = true
    }
    
    private lazy var userNameLabel = UILabel().apply { it in
        it.textAlignment = .left
        it.textColor = .black
        it.font = .systemFont(ofSize: 15)
        contentView.addSubview(it)
    }
    
    private var descriptionLabel = UILabel().apply { it in
        it.textAlignment = .left
        it.textColor = .darkGray
        it.numberOfLines = 2
        it.font = .systemFont(ofSize:13)
    }
    
    private var timeAgoLabel = UILabel().apply { it in
        it.textAlignment = .right
        it.textColor = .darkGray
        it.font = .systemFont(ofSize: 9)
    }
    
    private var tagView = LanguageTagView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
    }
    
    override func initializeView() {
        contentView.addSubview(iconImageView)
        contentView.addSubview(descriptionLabel)
        contentView.addSubview(tagView)
        contentView.addSubview(timeAgoLabel)
    }
    
    override func initializeConstraints() {
        
        // 10 + 70 + 10 + 20 + 10 = 120
        iconImageView.snp.makeConstraints { make in
            make.size.equalTo(imageSize)
            make.top.left.equalTo(10)
        }
        
        userNameLabel.snp.makeConstraints { make in
            make.left.equalTo(iconImageView)
//            make.width.equalTo(120)
            make.right.equalTo(-20)
            make.height.equalTo(20)
            make.bottom.equalTo(-10)
        }
        
        descriptionLabel.snp.makeConstraints { make in
            make.left.equalTo(iconImageView.snp.right).offset(20)
            make.height.equalTo(40)
            make.right.equalTo(-10)
            make.top.equalTo(15)
        }
        
        timeAgoLabel.snp.makeConstraints { make in
            make.width.equalTo(120)
            make.height.equalTo(8)
            make.right.equalTo(-10)
            make.top.equalTo(5)
        }
        
        tagView.snp.makeConstraints { make in
            make.left.equalTo(iconImageView.snp.right).offset(20)
            make.right.equalTo(-20)
            make.top.equalTo(descriptionLabel.snp.bottom)
            make.height.equalTo(20)
        }
    }
    
    func configure(gist: Gist) {
        Nuke.loadImage(with: gist.user.iconURL!, into: iconImageView)
        userNameLabel.text = "\(gist.user.name) / \(gist.files.first?.filename ?? "")"
        descriptionLabel.text = gist.description
        
        let languages: [String] = gist.files.map { $0.language ?? "???"}.uniqe
        timeAgoLabel.text = gist.createdAt.timeago()
        tagView.configure(languages: languages)
    }
}
