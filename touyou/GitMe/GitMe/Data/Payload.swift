//
//  Payload.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/31.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - Payload

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

// MARK: - Comment

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

// MARK: - Issue

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

// MARK: - Commit

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

// MARK: - Label

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

// MARK: - Forkee

struct Forkee: Codable {
}
