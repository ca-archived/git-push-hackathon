//
//  User.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/18.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

// MARK: - User

struct User: Codable {

    let login: String
    let id: Int
    let avatarUrl: URL

    enum CodingKeys: String, CodingKey {

        case login
        case id
        case avatarUrl = "avatar_url"
    }

    init(login: String, id: Int = 0, avatarUrl: URL) {

        self.login = login
        self.id = id
        self.avatarUrl = avatarUrl
    }
}
