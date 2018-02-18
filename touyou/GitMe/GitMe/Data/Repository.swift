//
//  Repository.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/18.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

struct Repository: Codable {

    let id: Int
    let name: String
    let url: URL
    let description: String?
    let updatedAt: Date?
    let stargazersCount: Int?
    let language: String?

    enum CodingKeys: String, CodingKey {
        case id
        case name
        case url
        case description
        case updatedAt = "updated_at"
        case stargazersCount = "stargazers_count"
        case language
    }
}

struct Readme: Codable {

    let downloadUrl: URL?

    enum CodingKeys: String, CodingKey {

        case downloadUrl = "download_url"
    }
}
