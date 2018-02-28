//
//  EventType.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/28.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation

enum EventType: String {
    case commitComment
    case create
    case delete
    case follow
    case fork
    case issueComment
    case issues
    case member
    case `public`
    case pullRequest
    case pullRequestReviewComment
    case push
    case watch
    
    var name: String {
        switch self {
        case .commitComment:
            return "CommitCommentEvent"
        case .create:
            return "CreateEvent"
        case .delete:
            return "DeleteEvent"
        case .follow:
            return "FollowEvent"
        case .fork:
            return "ForkEvent"
        case .issueComment:
            return "IssueCommentEvent"
        case .issues:
            return "IssuesEvent"
        case .member:
            return "MemberEvent"
        case .public:
            return "PublicEvent"
        case .pullRequest:
            return "PullRequestEvent"
        case .pullRequestReviewComment:
            return "PullRequestReviewCommentEvent"
        case .push:
            return "PushEvent"
        case .watch:
            return "WatchEvent"
        }
    }
    
    static func discription(for event: Event) -> String {
        switch event.type {
        case EventType.commitComment.name:
            return "\(event.actor.display_login) created a commit comment"
        case EventType.create.name:
            return "\(event.actor.display_login) created a repository \(event.repo.name)"
        case EventType.delete.name:
            return "\(event.actor.display_login) delete \(event.repo.name)"
        case EventType.follow.name:
            return "\(event.actor.display_login) follow another user"
        case EventType.fork.name:
            if let payload = event.payload, let forkee = payload.forkee, let fullName = forkee.full_name {
                return "\(event.actor.display_login) forked \(event.repo.name) to \(fullName)"
            }
        case EventType.issueComment.name:
            return "\(event.actor.display_login) comment on issue \(event.repo.name)"
        case EventType.issues.name:
            return "\(event.actor.display_login) open a issue"
        case EventType.member.name:
            if let payload = event.payload, let member = payload.member {
                return "\(event.actor.display_login) added \(member.login) as a collaborator to \(event.repo.name)"
            }
        case EventType.public.name:
            return "\(event.actor.display_login) has open sourced \(event.repo.name)"
        case EventType.pullRequest.name:
            return "\(event.actor.display_login) open pull request"
        case EventType.pullRequestReviewComment.name:
            return "\(event.actor.display_login) submitted a pull request review"
        case EventType.push.name:
            return "\(event.actor.display_login) pushed the branch to \(event.repo.name)"
        case EventType.watch.name:
            return "\(event.actor.display_login) starred \(event.repo.name)"
        default:
            return "Couldn't get description"
        }
        return "Couldn't get description"
    }
}
