//
//  DataSource.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation

protocol DataSourceDelegate: class {
    func finishedFetchDatas(error: GitHubAppError?)
}

final class DataSource {
    private var elements : [Activity] = [Activity]()
    private var page = 0
    private var isMoreAvailable = false
    private var isRefreshing = false
    
    weak var delegate: DataSourceDelegate?
    
    func count() -> Int {
        if isMoreAvailable {
            return elements.count + 1
        }
            return elements.count
    }
    
    func data(at row: Int) -> Activity? {
        if elements.count >= 0 && row < elements.count {
            return elements[row]
        }
        return nil
    }
    
    private func paging(_ page: inout Int) -> Int {
        page += 1
        return page
    }
    
    private func fetchingData(_ array: [Activity]?, shouldRefresh: Bool, error: GitHubAppError?) {
        isRefreshing = false
        
        if let _ = error {
            isMoreAvailable = false
            delegate?.finishedFetchDatas(error: nil)
            return
        }
        
        if shouldRefresh {
            elements.removeAll()
        }
        
        if let newElements = array, newElements.count > 0 {
            isMoreAvailable = true
            elements.append(contentsOf: newElements)
        } else {
            isMoreAvailable = false
        }
        delegate?.finishedFetchDatas(error: nil)
    }
    
    private func refreshDataSource(shouldRefresh: Bool) {
        if isRefreshing {
            return
        }
        isMoreAvailable = false
        isRefreshing = true
        APIClient<GetActivityEventsRequest>.sendRequest(request: GetActivityEventsRequest(page: paging(&page))){ (response, error) in
            self.fetchingData(response, shouldRefresh: shouldRefresh, error: error)
        }
    }
    
    func refreshAllData() {
        refreshDataSource(shouldRefresh: true)
    }
    
    func loadMoreData() {
        refreshDataSource(shouldRefresh: false)
    }
}
