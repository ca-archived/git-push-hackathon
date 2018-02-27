//
//  EventListPageViewController.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import UIKit

final class EventListPageViewController: UIViewController {
    
    fileprivate lazy var tableview: UITableView = {
        let tableView = UITableView(frame: self.view.bounds)
        tableView.delegate = self
        tableView.dataSource = self
        tableView.register(EventListCell.self, forCellReuseIdentifier: EventListCell.description())
        tableView.register(LoadMoreCell.self, forCellReuseIdentifier: LoadMoreCell.description())
        return tableView
    }()
    
    fileprivate lazy var dataSource: DataSource = {
        let dataSource = DataSource()
        dataSource.delegate = self
        return dataSource
    }()
    
    fileprivate lazy var refreshControl: UIRefreshControl = {
        let refreshControl = UIRefreshControl()
        refreshControl.addTarget(self, action: #selector(self.refreshData(_:)), for: .valueChanged)
        return refreshControl
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableview.refreshControl = refreshControl
        view.addSubview(tableview)
        
        dataSource.refreshAllData()
    }
    
    @objc private func refreshData(_ sender: Any) {
        dataSource.refreshAllData()
    }
}

extension EventListPageViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        guard let object = dataSource.data(at: indexPath.row) else {
            return LoadMoreCell.height()
        }
        return EventListCell.height(activity: object)
    }
}

extension EventListPageViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataSource.count()
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let object = dataSource.data(at: indexPath.row)
        if object == nil {
            guard let cell = tableView.dequeueReusableCell(withIdentifier: LoadMoreCell.description()) as? LoadMoreCell else { fatalError() }
            cell.startAnimating()
            dataSource.loadMoreData()
            return cell
        }
        guard let cell = tableView.dequeueReusableCell(withIdentifier: EventListCell.description()) as? EventListCell else { fatalError() }
        cell.activity = object
        return cell
    }
}

extension EventListPageViewController: DataSourceDelegate {
    func finishedFetchDatas(error: GitHubAppError?) {
        DispatchQueue.main.async {
            self.tableview.reloadData()
            self.refreshControl.endRefreshing()
        }
    }
}
