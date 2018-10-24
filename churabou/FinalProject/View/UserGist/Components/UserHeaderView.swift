//
//  UserHeaderView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/19.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import Nuke

final class UserHeaderView: BaseView {
    
    func configure(user: User) {
        if let url = user.iconURL {
            Nuke.loadImage(with:url, into: imageView)
        }
    }
    
    private let space: CGFloat = 10
    
    private var imageView = UIImageView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.backgroundColor = .red
        it.layer.cornerRadius = 30
        it.clipsToBounds = true
    }
    
    private var stackView = UIStackView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.spacing = 10
        it.axis = .horizontal
        it.distribution = .fillEqually
    }
    
    private (set) var followerButton = UIButton()
    
    func createSubItem(index: Int, title: String) -> UIButton {
        let button = UIButton()
        button.setTitle(title, for: .normal)
        button.titleLabel?.font = .systemFont(ofSize: 12)
        button.setTitleColor(.white, for: .normal)
        button.backgroundColor = .lightGray
        button.layer.cornerRadius = 20
        button.clipsToBounds = true // requied
        
        if index == 1 {
            followerButton = button
        }
        return button
    }
    
    override func initializeView() {
        
        ["???", "フォロワー", "???", "???"]
            .enumerated()
            .map(createSubItem)
            .forEach { stackView.addArrangedSubview($0) }
        
        addSubview(imageView)
        addSubview(stackView)
    }
    
    override func initializeConstraints() {
        
        imageView.snp.makeConstraints { make in
            make.size.equalTo(60)
            make.top.equalTo(40)
            make.centerX.equalToSuperview()
        }
        
        stackView.snp.makeConstraints { make in
            make.left.equalTo(space)
            make.right.equalTo(-space)
            make.bottom.equalTo(-15)
            make.height.equalTo(40)
        }
    }
}
