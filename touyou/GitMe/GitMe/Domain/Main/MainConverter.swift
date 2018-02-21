//
//  MainConverter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/27.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift

protocol MainConverterProtocol {

    var isLoggedIn: Bool { get }

    func fetchEvent(at page: Int, every perPage: Int) -> Observable<[EventCellViewModel]>
    func fetchLoginUserInfo() -> Observable<UserInfoViewModel>
}

class MainConverter {

    // MARK: Private

    private let api = GitHubAPI.shared

    private func convertEventTitle(of event: Event) -> NSMutableAttributedString {

        let mutableAttributedString = NSMutableAttributedString()
        mutableAttributedString.append([
            (event.actor.displayLogin, .bold)
            ])

        switch event.type {
        case .create:

            mutableAttributedString.append([
                (" created ", .normal),
                (event.repo!.name, .bold)
                ])
        case .issueComment:

            mutableAttributedString.append([
                (" \(event.payload.action!) comment on issue ", .normal),
                ("#\(event.payload.issue!.number)", .bold),
                (" in ", .normal),
                (event.repo!.name, .bold)
                ])
        case .issues:

            mutableAttributedString.append([
                (" \(event.payload.action!)  issue ", .normal),
                ("#\(event.payload.issue!.number)", .bold),
                (" in ", .normal),
                (event.repo!.name, .bold)
                ])
        case .push:

            mutableAttributedString.append([
                (" pushed to ", .normal),
                (String(event.payload.ref!.split(separator: "/").last!), .bold),
                (" at ", .normal),
                (event.repo!.name, .bold)
                ])
        case .watch:

            mutableAttributedString.append([
                (" starred ", .normal),
                (event.repo!.name, .bold)
                ])
        default:

            mutableAttributedString.append([
                (" \(event.type.rawValue): Unknown", .normal)
                ])
        }

        return mutableAttributedString
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
