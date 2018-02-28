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

    /// IssueCommnet, Issues, Watch, CommitComment
    let action: String?
    /// IssueComment, CommitComment
    let comment: Comment?
    /// Push, Create, Delete
    let ref: String?
    /// Create, Delete
    let refType: String?
    /// Fork
    let forkee: Forkee?
    /// IssueComment, Issues
    let issue: Issue?
    /// Member
    let member: User?
    /// OrgBlock
    let blockedUser: User?
    /// PullRequestReview
    let pullRequest: PullRequest?
    /// Release
    let release: Release?

    enum CodingKeys: String, CodingKey {
        case action
        case comment
        case ref
        case refType = "ref_type"
        case forkee
        case issue
        case member
        case blockedUser = "blocked_user"
        case pullRequest = "pull_request"
        case release
    }
}

// MARK: - Comment

struct Comment: Codable {

    let body: String
    let createdAt: Date
    let updatedAt: Date
    let url: URL
    let user: User

    enum CodingKeys: String, CodingKey {

        case body
        case createdAt = "created_at"
        case updatedAt = "updated_at"
        case url
        case user
    }
}

// MARK: - Issue

struct Issue: Codable {

    let body: String
    let number: Int
    let repositoryUrl: URL
    let state: String
    let title: String
    let updatedAt: Date
    let url: String
    let user: User

    enum CodingKeys: String, CodingKey {

        case body
        case number
        case repositoryUrl = "repository_url"
        case state
        case title
        case updatedAt = "updated_at"
        case url
        case user
    }
}

// MARK: - Forkee

struct Forkee: Codable {

    let fullName: String?

    enum CodingKeys: String, CodingKey {
        case fullName = "full_name"
    }
}

// MARK: - PullRequest

struct PullRequest: Codable {

    let number: Int
}

// MARK: - Release

struct Release: Codable {

    let tagName: String

    enum CodingKeys: String, CodingKey {
        case tagName = "tag_name"
    }
}
