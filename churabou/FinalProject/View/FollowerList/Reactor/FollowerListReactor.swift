//
//  FollowerListReactor.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import ReactorKit
import RxSwift


final class FollowerListReactor: Reactor {
    
    private var userName: String
    private var session: FollowerRepository
    
    init(user: User, session: FollowerRepository) {
        self.userName = user.name
        self.session = session
    }
    
    struct State {
        var users: [User] = []
        var page: Int? = 0
        var isLoading = false
        var selectedUser: User?
    }
    
    enum Action {
        case load
        case loadMore
        case selectUser(IndexPath)
    }
    
    enum Mutation {
        case setUser([User], nextPage: Int?)
        case appendUser([User], nextPage: Int?)
        case setLoading(Bool)
        case setSelectedUser(User?)
    }
    
    var initialState = State()
    
    func mutate(action: Action) -> Observable<Mutation> {
        switch action {
        case .load:
            return Observable.concat([
                Observable.just(Mutation.setSelectedUser(nil)),
                session
                    .feachFollower(userName: userName, page: 1)
                    .map { Mutation.setUser($0.0, nextPage: $0.1) }
                ])
        case .loadMore:
            if currentState.isLoading { return .empty() }
            guard let page = currentState.page else {
                return .empty()
            }
            
            return Observable.concat([
                Observable.just(Mutation.setLoading(true)),
                session.feachFollower(userName: userName, page: page)
                    .map { Mutation.appendUser($0.0, nextPage: $0.1) },
                Observable.just(Mutation.setLoading(false))
            ])
        case .selectUser(let indexPath):
            let user = currentState.users[indexPath.row]
            return Observable.just(Mutation.setSelectedUser(user))
        }
    }

    func reduce(state: State, mutation: Mutation) -> State {
        switch mutation {
        case .setUser(let users, let page):
             var newState = state
            newState.users = users
            newState.page = page
            return newState
        case .appendUser(let users, let page):
             var newState = state
            newState.users.append(contentsOf: users)
            newState.page = page
            return newState
        case .setLoading(let isLoading):
             var newState = state
            newState.isLoading = isLoading
            return newState
        case .setSelectedUser(let user):
            var newState = state
            newState.selectedUser = user
            return newState
        }
    }
}
