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
    var readmeObservable: Observable<ReadmeViewModel>!
    
    init() {}
}

class RepositoryViewModel {
    
    var repositoryDescription: String?
    var languageColor: UIColor!
    var language: String?
    var starCount: Int!
    var starString: String {
        
        if starCount >= 10000 {
            return "\(starCount / 1000)K"
        }
        return "\(starCount ?? 0)"
    }
    var repositoryCreatedAt: Date!
    var repoInfo: String {

        if let date = repositoryCreatedAt {

            let dateFormat = DateFormatter()
            dateFormat.dateStyle = .short
            dateFormat.timeStyle = .none
            dateFormat.locale = Locale(identifier: "ja_JP")
            return "\(language ?? "")   ★\(starString)  Updated \(dateFormat.string(from: date))"
        }
        return "\(language ?? "None")   ★\(starString)"
    }
    
    init() {}
}

class ReadmeViewModel {
    
    var markDownString: String?
    
    init() {}
}
