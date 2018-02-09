//
//  MainViewModel.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/27.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift

class EventCellViewModel {

    var iconUrl: URL!
    var eventTitle: NSMutableAttributedString!
    var createAt: Date!
    var repositoryName: String!
    var repositoryDescription: String!
    var languageColor: UIColor!
    var language: String!
    var starCount: Int!
    var starString: String {

        if starCount >= 10000 {
            return "\(starCount / 1000)K"
        }
        return "\(starCount)"
    }
    var repositoryCreatedAt: Date!

    init() {}
}
