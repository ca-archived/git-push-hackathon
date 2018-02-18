//
//  User.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/18.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

struct User: Codable {

    struct Plan: Codable {

        let name: String
        let space: Int
        let privateReposCount: Int
        let collaboratorsCount: Int

        enum CodingKeys: String, CodingKey {

            case name
            case space
            case privateReposCount = "private_repos"
            case collaboratorsCount = "collaborators"
        }
    }

    let login: String
    let id: Int
    let avatarUrl: URL
    let gravatarId: String?
    let url: URL
    let htmlUrl: URL
    let followersUrl: URL
    let followingUrlString: String
    let gistsUrlString: String
    let starredUrlString: String
    let subscriptionsUrl: URL
    let organizationsUrl: URL
    let reposUrl: URL
    let eventsUrlString: String
    let receivedEventsUrl: URL
    let type: String
    let isSiteAdmin: Bool
    let name: String?
    let company: String?
    let blog: String?
    let location: String?
    let email: String?
    let isHireable: Bool?
    let bio: String?
    let publicReposCount: Int?
    let publicGistsCount: Int?
    let followersCount: Int?
    let followingCount: Int?
    let createdAt: Date?
    let updatedAt: Date?
    let totalPrivateReposCount: Int?
    let ownedPrivateReposCount: Int?
    let privateGistsCount: Int?
    let diskUsage: Int?
    let collaboratorsCount: Int?
    let isTwoFactorAuth: Bool?
    let plan: Plan?

    enum CodingKeys: String, CodingKey {

        case login
        case id
        case avatarUrl = "avatar_url"
        case gravatarId = "gravatar_id"
        case url
        case htmlUrl = "html_url"
        case followersUrl = "followers_url"
        case followingUrlString = "following_url"
        case gistsUrlString = "gists_url"
        case starredUrlString = "starred_url"
        case subscriptionsUrl = "subscriptions_url"
        case organizationsUrl = "organizations_url"
        case reposUrl = "repos_url"
        case eventsUrlString = "events_url"
        case receivedEventsUrl = "received_events_url"
        case type
        case isSiteAdmin = "site_admin"
        case name
        case company
        case blog
        case location
        case email
        case isHireable = "hireable"
        case bio
        case publicReposCount = "public_repos"
        case publicGistsCount = "public_gists"
        case followersCount = "followers"
        case followingCount = "following"
        case createdAt = "created_at"
        case updatedAt = "updated_at"
        case totalPrivateReposCount = "total_private_repos"
        case ownedPrivateReposCount = "owned_private_repos"
        case privateGistsCount = "private_gists"
        case diskUsage = "disk_usage"
        case collaboratorsCount = "collaborators"
        case isTwoFactorAuth = "two_factor_authentication"
        case plan
    }
}
