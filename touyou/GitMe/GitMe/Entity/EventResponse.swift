//
//  EventResponse.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/31.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

struct EventResponse: Codable {

    let actor: Actor
    private let createdAtString: String
    var createdAt: Date {

        return Date()
    }
    let id: UInt64
    let org: Organization?
    let payload: Payload
    let repo: Repository?
    let isPublic: Bool
    let typeString: String

    enum CodingKeys: String, CodingKey {
        case actor
        case createdAtString = "created_at"
        case id
        case org
        case payload
        case repo
        case isPublic = "public"
        case typeString = "type"
    }
}

struct Actor: Codable {

    private let avatarUrlString: String
    var avatarUrl: URL? {

        return URL(string: self.avatarUrlString)
    }
    let displayLogin: String
    let gravatarId: String?
    let id: UInt64
    let login: String
    private let urlString: String
    var url: URL? {

        return URL(string: self.urlString)
    }

    enum CodingKeys: String, CodingKey {
        case avatarUrlString = "avatar_url"
        case displayLogin = "display_login"
        case gravatarId = "gravatar_id"
        case id
        case login
        case urlString = "url"
    }
}

struct Organization: Codable {

    private let avatarUrlString: String
    var avatarUrl: URL? {

        return URL(string: self.avatarUrlString)
    }
    let gravatarId: String?
    let id: UInt64
    let login: String
    private let urlString: String
    var url: URL? {

        return URL(string: self.urlString)
    }

    enum CodingKeys: String, CodingKey {
        case avatarUrlString = "avatar_url"
        case gravatarId = "gravatar_id"
        case id
        case login
        case urlString = "url"
    }
}

struct Payload: Codable {

    let action: String?
    let comment: Comment?
    let issue: Issue?
    let before: String?
    let commits: [Commit]?
    let distinctSize: Int?
    let head: String?
    let pushId: String?
    let ref: String?
    let refType: String?
    let size: String?
    let description: String?
    let masterBranch: String?
    let pusherType: String?

    enum CodingKeys: String, CodingKey {
        case action
        case comment
        case issue
        case before
        case commits
        case distinctSize = "distinct_size"
        case head
        case pushId = "push_id"
        case ref
        case refType = "ref_type"
        case size
        case description
        case masterBranch = "master_branch"
        case pusherType = "pusher_type"
    }
}

struct Repository: Codable {

    let id: UInt64
    let name: String
    private let urlString: String
    var url: URL? {

        return URL(string: self.urlString)
    }

    enum CodingKeys: String, CodingKey {
        case id
        case name
        case urlString = "url"
    }
}

struct User: Codable {
}

struct UserResponse: Codable {

    let login: String
    let id: UInt64
    private let avatarUrl: String
    let gravatarId: String?
    private let urlString: String
    private let htmlUrlString: String
    private let followersUrlString: String
    private let followingUrlString: String
    private let gistsUrlString: String
    private let starredUrlString: String
    private let subscriptionsUrlString: String
    private let organizationsUrlString: String
    private let reposUrlString: String
    private let eventsUrlString: String
    private let receivedEventsUrlString: String
    let type: String
    let isSiteAdmin: Bool
    let name: String
    let company: String
    let blog: String
    let location: String
    let email: String
    let isHireable: Bool
    let bio: String
    let publicReposCount: Int
    let publicGistsCount: Int
    let followersCount: Int
    let followingCount: Int
    private let createdAtString: String
    private let updatedAtString: String
    let totalPrivateReposCount: Int
    let ownedPrivateReposCount: Int
    let privateGistsCount: Int
    let diskUsage: Int
    let collaboratorsCount: Int
    let isTwoFactorAuth: Bool
    let plan: Plan

    enum CodingKeys: String, CodingKey {

        case login
        case id
        case avatarUrl = "avatar_url"
        case gravatarId = "gravatar_id"
        case urlString = "url"
        case htmlUrlString = "html_url"
        case followersUrlString = "followers_url"
        case followingUrlString = "following_url"
        case gistsUrlString = "gists_url"
        case starredUrlString = "starred_url"
        case subscriptionsUrlString = "subscriptions_url"
        case organizationsUrlString = "organizations_url"
        case reposUrlString = "repos_url"
        case eventsUrlString = "events_url"
        case receivedEventsUrlString = "received_events_url"
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
        case createdAtString = "created_at"
        case updatedAtString = "updated_at"
        case totalPrivateReposCount = "total_private_repos"
        case ownedPrivateReposCount = "owned_private_repos"
        case privateGistsCount = "private_gists"
        case diskUsage = "disk_usage"
        case collaboratorsCount = "collaborators"
        case isTwoFactorAuth = "two_factor_authentication"
        case plan
    }
}

struct Plan: Codable {

}

struct Comment: Codable {

    let authorAssocitation: String
    let body: String
    let createdAtString: String
    let htmlUrlString: String
    let id: UInt64
    let issueUrlString: String
    let updatedAtString: String
    let urlString: String
    let user: User
}

struct Issue: Codable {

}

struct Commit: Codable {

}
