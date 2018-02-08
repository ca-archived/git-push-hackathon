//
//  EventResponse.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/31.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

/// 必要な情報だけデコード

struct Event: Codable {

    let actor: Actor
    let createdAt: Date
    let id: String
    let org: Organization?
    let payload: Payload
    let repo: Repository?
    let isPublic: Bool
    let type: EventType

    enum CodingKeys: String, CodingKey {
        case actor
        case createdAt = "created_at"
        case id
        case org
        case payload
        case repo
        case isPublic = "public"
        case type
    }

    enum EventType: String, Codable {
        case commitComment = "CommitCommentEvent"
        case create = "CreateEvent"
        case delete = "DeleteEvent"
        case deployment = "DeploymentEvent"
        case deploymentStatus = "DeplymentStatusEvent"
        case fork = "ForkEvent"
        case gollum = "GollumEvent"
        case installation = "InstallationEvent"
        case installationRepositories = "InstallationRepositoriesEvent"
        case issueComment = "IssueCommentEvent"
        case issues = "IssuesEvent"
        case label = "LabelEvent"
        case marketplacePurchase = "MarketplacePurchaseEvent"
        case member = "MemberEvent"
        case membership = "MembershipEvent"
        case milestone = "MilestoneEvent"
        case organization = "OrganizationEvent"
        case orgBlock = "OrgBlockEvent"
        case pageBuild = "PageBuildEvent"
        case projectCard = "ProjectCardEvent"
        case projectColumn = "ProjectColumnEvent"
        case project = "ProjectEvent"
        case `public` = "PublicEvent"
        case pullRequest = "PullRequestEvent"
        case pullRequestReview = "PullRequestReviewEvent"
        case pullRequestReviewComment = "PullRequestReviewCommentEvent"
        case push = "PushEvent"
        case release = "ReleaseEvent"
        case repository = "RepositoryEvent"
        case status = "StatusEvent"
        case team = "TeamEvent"
        case teamAdd = "TeamAddEvent"
        case watch = "WatchEvent"
    }
}

struct Actor: Codable {

    let avatarUrl: URL
    let displayLogin: String
    let gravatarId: String?
    let id: Int
    let login: String
    let url: URL

    enum CodingKeys: String, CodingKey {
        case avatarUrl = "avatar_url"
        case displayLogin = "display_login"
        case gravatarId = "gravatar_id"
        case id
        case login
        case url
    }
}

struct Organization: Codable {

    let avatarUrl: URL
    let gravatarId: String?
    let id: Int
    let login: String
    let url: URL

    enum CodingKeys: String, CodingKey {
        case avatarUrl = "avatar_url"
        case gravatarId = "gravatar_id"
        case id
        case login
        case url
    }
}

struct Payload: Codable {

    /// IssueCommnet, Issues, Watch
    let action: String?
    /// IssueComment
    let comment: Comment?
    /// IssueComment, Issues
    let issue: Issue?
    /// Push
    let before: String?
    /// Push
    let commits: [Commit]?
    /// Push
    let distinctSize: Int?
    /// Push
    let head: String?
    /// Push
    let pushId: Int?
    /// Push, Create
    let ref: String?
    /// Create
    let refType: String?
    /// Push
    let size: Int?
    /// Create
    let description: String?
    /// Create
    let masterBranch: String?
    /// Create
    let pusherType: String?
    /// Fork
    let forkee: Forkee?

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
        case forkee
    }
}

struct Repository: Codable {

    let id: Int
    let name: String
    let url: URL

    enum CodingKeys: String, CodingKey {
        case id
        case name
        case url
    }
}

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

struct Comment: Codable {

    let authorAssociation: String
    let body: String
    let createdAt: Date
    let htmlUrl: URL
    let id: Int
    let issueUrl: URL
    let updatedAt: Date
    let url: URL
    let user: User

    enum CodingKeys: String, CodingKey {

        case authorAssociation = "author_association"
        case body
        case createdAt = "created_at"
        case htmlUrl = "html_url"
        case id
        case issueUrl = "issue_url"
        case updatedAt = "updated_at"
        case url
        case user
    }
}

struct Issue: Codable {

    let assignee: String?
    let assignees: [String]
    let authorAssociation: String?
    let body: String
    let closedAt: Date?
    let commentsCount: Int
    let commentsUrl: URL
    let createdAt: Date
    let eventsUrl: URL
    let htmlUrl: URL
    let id: Int
    let labels: [Label]
    let labelsUrlString: String
    let lockedCount: Int
    let milestone: String?
    let number: Int
    let repositoryUrl: URL
    let state: String
    let title: String
    let updatedAt: Date
    let url: String
    let user: User

    enum CodingKeys: String, CodingKey {

        case assignee
        case assignees
        case authorAssociation = "author_association"
        case body
        case closedAt = "closed_at"
        case commentsCount = "comments"
        case commentsUrl = "comments_url"
        case createdAt = "created_at"
        case eventsUrl = "events_url"
        case htmlUrl = "html_url"
        case id
        case labels
        case labelsUrlString = "labels_url"
        case lockedCount = "locked"
        case milestone
        case number
        case repositoryUrl = "repository_url"
        case state
        case title
        case updatedAt = "updated_at"
        case url
        case user
    }
}

struct Commit: Codable {

    struct Author: Codable {

        let email: String
        let name: String
    }

    let author: Author
    let distinct: Int
    let message: String
    let sha: String
    let url: URL
}

struct Label: Codable {

    let colorString: String
    let `default`: Int
    let id: Int
    let name: String
    let url: URL

    enum CodingKeys: String, CodingKey {
        case colorString = "color"
        case `default`
        case id
        case name
        case url
    }
}

struct Forkee: Codable {
}
