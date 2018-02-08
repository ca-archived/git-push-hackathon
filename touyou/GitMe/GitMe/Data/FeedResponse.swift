//
//  FeedResponse.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/30.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

struct FeedResponse: Codable {

    private let currentUserPublicUrlString: String
    private let timelineUrlString: String
    private let userUrlString: String
    var currentUserPublicUrl: URL? {

        return URL(string: self.currentUserPublicUrlString)
    }
    var timelineUrl: URL? {

        return URL(string: self.timelineUrlString)
    }
    let links: FeedLink

    enum CodingKeys: String, CodingKey {
        case currentUserPublicUrlString = "current_user_public_url"
        case timelineUrlString = "timeline_url"
        case userUrlString = "user_url"
        case links = "_links"
    }
}

struct FeedLink: Codable {

    let currentUserPublic: LinkInfo
    let timeline: LinkInfo
    let user: LinkInfo

    enum CodingKeys: String, CodingKey {
        case currentUserPublic = "current_user_public"
        case timeline
        case user
    }
}

struct LinkInfo: Codable {

    private let hrefString: String
    var href: URL? {

        return URL(string: self.hrefString)
    }
    let type: String

    enum CodingKeys: String, CodingKey {
        case hrefString = "href"
        case type
    }
}
