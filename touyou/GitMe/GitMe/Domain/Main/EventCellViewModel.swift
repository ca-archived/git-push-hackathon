//
//  EventCellViewModel.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/27.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift

class EventCellViewModel {
    
    var repositoryName: String!
    var iconUrl: URL!
    var eventTitle: NSMutableAttributedString!
    var createAt: Date!
    var repoObservable: Observable<RepositoryViewModel>!
    var readmeObservable: Observable<URL?>!
    var isShowReadme: Bool = false
    var repositoryDescription: String?
    var repositoryInfo: String?
    var readmeUrl: URL?

    init() {}
}

class RepositoryViewModel {
    
    var repositoryDescription: String?
    var repoInfo: String?
    
    init() {}
}
