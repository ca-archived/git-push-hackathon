//
//  FollowerListView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/20.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import SnapKit

final class FollowerListView: BaseView {
    
    private (set) lazy var tableView = UITableView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.register(FollowerCell.self, forCellReuseIdentifier: "cell")
        it.delegate = self
    }
    
    private (set) var indicator = UIActivityIndicatorView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.color = .orange
    }
    
    override func initializeView() {
        addSubview(tableView)
        addSubview(indicator)
    }
    
    override func initializeConstraints() {
        tableView.snp.makeConstraints { $0.edges.equalToSuperview() }
        indicator.snp.makeConstraints { $0.edges.equalToSuperview() }
    }
}

extension FollowerListView: UITableViewDelegate {

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return FollowerCell.height
    }
}
