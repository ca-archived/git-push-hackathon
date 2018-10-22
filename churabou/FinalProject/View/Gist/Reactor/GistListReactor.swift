//
//  GistListReactor.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/17.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import ReactorKit
import RxSwift

final class GistListReactor: Reactor {

    private var session: GistRepository
    
    init(session: GistRepository) {
        self.session = session
    }

    struct State {
        var gists: [Gist] = []
        var page: Int? = 0
        var isLoading = false
        var isRefreshing = false
        var selectedGist: Gist?
    }
    
    enum Action {
        case load
        case loadMore
        case selectGist(IndexPath)
        case viewDidDisappear
    }
    
    enum Mutation {
        case setGist([Gist], nextPage: Int?)
        case appendGists([Gist], nextPage: Int?)
        case setLoading(Bool)
        case setRefreshing(Bool)
        case setSelectedGist(Gist?)
    }
    
    var initialState = State()
    
    func mutate(action: Action) -> Observable<Mutation> {
        switch action {
        case .load:
            print("loadloadload")
            return Observable.concat([
                Observable.just(Mutation.setRefreshing(true)),
                session.feachGist(page: 1)
                    .map { Mutation.setGist($0.0, nextPage: $0.1) },
                Observable.just(Mutation.setRefreshing(false))
                ])
        case .loadMore:
            if currentState.isLoading { return .empty() }
            guard let page = currentState.page else {
                return .empty()
            }
            
            return Observable.concat([
                Observable.just(Mutation.setLoading(true)),
                session.feachGist(page: page)
                    .map { Mutation.appendGists($0.0, nextPage: $0.1) },
                Observable.just(Mutation.setLoading(false))
                ])
        case .selectGist(let indexPath):
            let gist = currentState.gists[indexPath.row]
            return Observable.just(Mutation.setSelectedGist(gist))
        case .viewDidDisappear:
            return Observable.just(Mutation.setSelectedGist(nil))
        }
    }
    
    func reduce(state: State, mutation: Mutation) -> State {
        var newState = state
        switch mutation {
        case .setGist(let gists, let page):
            newState.gists = gists
            newState.page = page
        case .appendGists(let gists, let page):
            newState.gists.append(contentsOf: gists)
            newState.page = page
        case .setLoading(let isLoading):
            newState.isLoading = isLoading
        case .setRefreshing(let isRefreshing):
            newState.isRefreshing = isRefreshing
        case .setSelectedGist(let gist):
            newState.selectedGist = gist
        }
        return newState
    }
}
