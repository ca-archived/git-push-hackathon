//
//  GistListView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/19.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

final class GistListView: BaseView {
    
    private (set) lazy var tableView = UITableView().apply { it in
        it.register(GistTableViewCell.self, forCellReuseIdentifier: "cell")
        it.addSubview(refresh)
        it.delegate = self
    }
    
    private (set) var indicator = UIActivityIndicatorView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.color = .blue
    }
    
    private (set) var refresh = UIRefreshControl()
    
    override func initializeView() {
        backgroundColor = .white
        addSubview(tableView)
        addSubview(indicator)
    }
    
    override func initializeConstraints() {
        tableView.snp.makeConstraints { $0.edges.equalToSuperview() }
        indicator.snp.makeConstraints { $0.edges.equalToSuperview() }
    }
}

extension GistListView: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return GistTableViewCell.height
    }
}

