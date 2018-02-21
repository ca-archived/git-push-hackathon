//
//  Event.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/18.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

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
