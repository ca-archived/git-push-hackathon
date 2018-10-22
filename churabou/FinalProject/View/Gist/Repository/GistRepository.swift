//
//  GistRepository.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/19.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation
import RxSwift

protocol GistRepository {
    func feachGist(page: Int) -> Observable<([Gist], Int?)>
}

struct GistSession: GistRepository {

    func feachGist(page: Int) -> Observable<([Gist], Int?)> {

            let request = GithubGistRequest.Get(target: .public, page: page)
            return Observable.just(request)
                .flatMapLatest { APIClient().response(from: $0)
                    .observeOn(MainScheduler.instance)
                    .catchErrorJustReturn([])
                }
                .map { ($0, $0.isEmpty ? nil : page + 1) }
    }
}



