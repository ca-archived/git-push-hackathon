//
//  UserGistReactor.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/16.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import RxSwift
import ReactorKit
import RxCocoa

protocol UserGistViewModelInput {
    func viewWillAppear()
    func refresh()
    func requestDeleteGist(at indexPath: IndexPath)
    func selectGist(at indexPath: IndexPath)
    func tapFollower()
}

protocol UserGistViewModelOutput {
    var gists: BehaviorRelay<[Gist]> { get }
    var loadingGist: Observable<Bool> { get }
    var deletingGist: Observable<Bool> { get }
    var errorMessage: Observable<String> { get }
    var tableViewUpdate: Observable<UserGistViewModel.TableViewLoadType> { get }
    var showGist: Observable<Gist> { get }
    var showFollower: Observable<User> { get }
}

protocol UserGistViewModelUsecase {
    func deleteGist(at indexPath: IndexPath)
    func featchGist(userName: String)
}

protocol UserGistViewModelType {
    var input: UserGistViewModelInput { get }
    var output: UserGistViewModelOutput { get }
}


final class UserGistViewModel: UserGistViewModelType {
    
    private var user: User
    private var router: UserGistRouter
    init(user: User, router: UserGistRouter) {
        self.user = user
        self.router = router
    }

    private var storedGists: BehaviorRelay<[Gist]> = BehaviorRelay(value: [])
    private var isLoadingGist = BehaviorRelay(value: false)
    private var isDeletingGist = BehaviorRelay(value: false)
    
    enum TableViewLoadType {
        case reload
        case delete(IndexPath)
    }
    
    private var tableViewReloadTrigger = PublishSubject<TableViewLoadType>()
    private var errorMessageTrigger = PublishSubject<String>()
    private var showGistTrigger = PublishSubject<Gist>()
    private var showFollowerTrigger = PublishSubject<User>()

    private let api = APIClient()

    func loadMoreGist() {
        // If Needed
    }
    
    var input: UserGistViewModelInput { return self }
    var output: UserGistViewModelOutput { return self }
    private var usecase: UserGistViewModelUsecase { return self }
}

extension UserGistViewModel: UserGistViewModelInput {
    
    func viewWillAppear() {
        usecase.featchGist(userName: user.name)
    }
    
    func refresh() {
        usecase.featchGist(userName: user.name)
    }
    
    func requestDeleteGist(at indexPath: IndexPath) {
        usecase.deleteGist(at: indexPath)
    }
    
    func selectGist(at indexPath: IndexPath) {
        let gist = gists.value[indexPath.row]
        showGistTrigger.onNext(gist)
    }
    
    func tapFollower() {
        showFollowerTrigger.onNext(user)
    }
}

extension UserGistViewModel: UserGistViewModelOutput {
    
    var gists: BehaviorRelay<[Gist]> {
        return storedGists
    }

    var loadingGist: Observable<Bool> {
        return isLoadingGist.asObservable()
    }
    
    var deletingGist: Observable<Bool> {
        return isDeletingGist.asObservable()
    }
    
    var errorMessage: Observable<String> {
        return errorMessageTrigger.asObservable()
    }
    
    var tableViewUpdate: Observable<TableViewLoadType> {
        return tableViewReloadTrigger.asObservable()
    }
    
    var showGist: Observable<Gist> {
        return showGistTrigger.asObservable()
    }
    
    var showFollower: Observable<User> {
        return showFollowerTrigger.asObservable()
    }
}

extension UserGistViewModel: UserGistViewModelUsecase {
    
    func featchGist(userName: String) {
        if isLoadingGist.value { return }
        let request = GithubGistRequest.Get(target: .user(userName))
        isLoadingGist.accept(true)
        api.send(request) { response in
            self.isLoadingGist.accept(false)
            switch response {
            case .success(let gists):
                self.storedGists.accept(gists)
                self.tableViewReloadTrigger.onNext(.reload)
            case .failure(let message):
                self.errorMessageTrigger.onNext(message)
            }
        }
    }
    
    func deleteGist(at indexPath: IndexPath) {
        
        if isDeletingGist.value { return }
        let request = GithubGistRequest.Delete(id: storedGists.value[indexPath.row].id)
        isDeletingGist.accept(true)
        api.send(request) { response in
            self.isDeletingGist.accept(false)
            switch response {
            case .success:
                var old = self.storedGists.value
                old.remove(at: indexPath.row)
                self.storedGists.accept(old)
                self.tableViewReloadTrigger.onNext(.delete(indexPath))
            case .failure(let message):
                self.errorMessageTrigger.onNext(message)
            }
        }
    }
}
