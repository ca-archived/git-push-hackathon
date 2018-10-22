//
//  UserGistView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/13.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

final class UserGistView: BaseView {
    
    private (set) var refresh = UIRefreshControl()

    private (set) lazy var tableView = UITableView().apply { it in
        it.register(GistTableViewCell.self, forCellReuseIdentifier: "cell")
        it.addSubview(refresh)
    }
    
    private (set) var header = UserHeaderView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.backgroundColor = .white
    }
    
    private (set) var indicator = UIActivityIndicatorView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.color = .blue
    }
    
    private (set) var noGistView = UILabel().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.text = "投稿されたGistがありません"
        it.textAlignment = .center
        it.textColor = .white
        it.isHidden = true
        it.isEnabled = false
        it.backgroundColor = .black
    }
    
    override func initializeView() {
        tableView.addSubview(refresh)
        addSubview(tableView)
        addSubview(header)
        addSubview(indicator)
        addSubview(noGistView)
    }
    
    override func initializeConstraints() {
        
        header.snp.makeConstraints { make in
            make.top.left.right.equalToSuperview()
            make.height.equalTo(170)
        }
        
        tableView.snp.makeConstraints { make in
            make.top.equalTo(header.snp.bottom)
            make.left.bottom.right.equalToSuperview()
        }
        
        indicator.snp.makeConstraints {
            $0.edges.equalToSuperview()
        }
        
        noGistView.snp.makeConstraints { make in
            make.top.equalTo(header.snp.bottom)
            make.left.bottom.right.equalToSuperview()
        }
    }
}


