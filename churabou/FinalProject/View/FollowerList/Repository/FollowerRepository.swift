//
//  FollowerRepository.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/20.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation
import RxSwift

protocol FollowerRepository {
    func feachFollower(userName: String, page: Int) -> Observable<([User], Int?)>
}

struct FollowerSession: FollowerRepository {
    
    func feachFollower(userName: String, page: Int) -> Observable<([User], Int?)> {
        
        let request = FollowerRequest(username: userName, page: page)
        return Observable.just(request)
            .flatMapLatest { APIClient().response(from: $0) }
            .map { ($0, $0.isEmpty ? nil : page + 1) }
    }
}
