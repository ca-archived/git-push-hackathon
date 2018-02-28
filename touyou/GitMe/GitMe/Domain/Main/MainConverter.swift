//
//  MainConverter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/27.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift

// MARK: - MainConverterProtocol

protocol MainConverterProtocol {

    var isLoggedIn: Bool { get }

    func fetchEvent(at page: Int, every perPage: Int) -> Observable<[EventCellViewModel]>
    func fetchLoginUserInfo() -> Observable<UserInfoViewModel>
}

// MARK: - MainConverter

class MainConverter {

    // MARK: Private

    private let api = GitHubAPI.shared

    private func convertEventTitle(of event: Event) -> NSMutableAttributedString {

        let mutableAttributedString = NSMutableAttributedString()
        mutableAttributedString.append([(event.actor.displayLogin, .bold)])
        mutableAttributedString.append(createMutableTemplate(of: event))
        return mutableAttributedString
    }

    /// イベントに応じてメッセージをつくる。
    private func createMutableTemplate(of event: Event) -> [(String, NSMutableAttributedString.GitMeStyle)] {

        switch event.type {
        case .commitComment:

            return [(" \(event.payload.action!) comment \"\(event.payload.comment!.body)\"", .normal)]
        case .create:

            if let ref = event.payload.ref {

                return [(" created \(event.payload.refType!) ", .normal), (ref, .bold)]
            } else {

                return [(" created repository", .normal)]
            }
        case .delete:

            if let ref = event.payload.ref {

                return [(" deleted \(event.payload.refType!) ", .normal), (ref, .bold)]
            } else {

                return [(" deleted repository", .normal)]
            }
        case .fork:

            return [(" forked ", .normal), (event.payload.forkee!.fullName!, .bold)]
        case .gollum:

            return [(" did wiki page action", .normal)]
        case .installation:

            return [(" \(event.payload.action!) installation", .normal)]
        case .installationRepositories:

            return [(" \(event.payload.action!) installation", .normal)]
        case .issueComment:

            return [(" \(event.payload.action!) comment on issue ", .normal), ("#\(event.payload.issue!.number)", .bold)]
        case .issues:

           return [(" \(event.payload.action!) issue ", .normal), ("#\(event.payload.issue!.number)", .bold)]
        case .marketplacePurchase:

            return [(" \(event.payload.action!) marketplace", .normal)]
        case .member:

            return [(" \(event.payload.action!) ", .normal), (event.payload.member!.login, .bold)]
        case .orgBlock:

            return [(" \(event.payload.action!) ", .normal), (event.payload.member!.login, .bold)]
        case .projectCard:

            return [(" \(event.payload.action!) project card", .normal)]
        case .projectColumn:

            return [(" \(event.payload.action!) project column", .normal)]
        case .project:

            return [(" \(event.payload.action!) project", .normal)]
        case .public:

            return [(" made public", .normal)]
        case .pullRequest:

            return [(" \(event.payload.action!) pull request ", .normal), ("#\(event.payload.pullRequest!.number)", .bold)]
        case .pullRequestReview:

            return [(" \(event.payload.action!) review on pull request ", .normal), ("#\(event.payload.pullRequest!.number)", .bold)]
        case .pullRequestReviewComment:

            return [(" \(event.payload.action!) review comment on pull request ", .normal), ("#\(event.payload.pullRequest!.number)", .bold)]
        case .push:

            return [(" pushed to ", .normal), (event.payload.ref!.split(separator: "/").dropFirst(2).joined(separator: "/"), .bold)]
        case .release:

            return [(" \(event.payload.action!) \(event.payload.release!.tagName)", .normal)]
        case .watch:

            return [(" starred", .normal)]
        default:

            // ドキュメントを見る限りこれ意外はタイムラインとしては流れてこないということになっているはずなので
            return [(" created wrong event.", .normal)]
        }
    }

    private func convertRepositoryInfo(of repo: Repository) -> String {

        let starCount = repo.stargazersCount ?? 0
        let starString = starCount >= 1000 ? "\(starCount / 1000)K" : "\(starCount)"
        if let dateString = repo.updatedAt?.dateString {

            return "\(repo.language ?? "None")   ★\(starString)  Updated \(dateString)"
        }

        return "\(repo.language ?? "None")   ★\(starString)"
    }
}

// MARK: - Protocol Interface

extension MainConverter: MainConverterProtocol {

    var isLoggedIn: Bool {

        return api.isLoggedIn
    }

    func fetchEvent(at page: Int, every perPage: Int) -> Observable<[EventCellViewModel]> {

        return api.fetchEvents(at: page, every: perPage).map { events in

            events.map { event in

                let eventCellViewModel = EventCellViewModel()
                eventCellViewModel.iconUrl = event.actor.avatarUrl
                eventCellViewModel.eventTitle = self.convertEventTitle(of: event)
                eventCellViewModel.createAt = event.createdAt
                eventCellViewModel.repositoryName = event.repo?.name
                let (repo, readme) = self.api.fetchRepositoryInfo(of: event.repo!.url)
                eventCellViewModel.repoObservable = repo.map { (repository: Repository) in

                    let repoViewModel = RepositoryViewModel()
                    repoViewModel.repositoryDescription = repository.description
                    repoViewModel.repoInfo = self.convertRepositoryInfo(of: repository)
                    return repoViewModel
                }.share()
                eventCellViewModel.readmeObservable = readme.map { readme in

                    return readme.downloadUrl
                }.share()

                return eventCellViewModel
            }
        }
    }

    func fetchLoginUserInfo() -> Observable<UserInfoViewModel> {

        return api.logIn().map { user in

            let userInfoViewModel = UserInfoViewModel()
            userInfoViewModel.userName = user.login
            userInfoViewModel.iconUrl = user.avatarUrl
            return userInfoViewModel
        }
    }
}
