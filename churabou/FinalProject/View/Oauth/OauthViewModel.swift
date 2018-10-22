//
//  OauthViewModel.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/09.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation
import RxSwift

final class OauthViewModel {
    
    private lazy var githubURL: String = {
        return "https://github.com/login/oauth/authorize?client_id=\(AppConfig.clientId)&scope=gist"
    }()
    
    private var feachTokenSubject = PublishSubject<Void>()
    private var loginRequestTrigger = PublishSubject<URLRequest>()
    // input
    var viewWillAppear = PublishSubject<Void>()
    var requestAccessToken = PublishSubject<String>()
    // output
    var feachToken: Observable<Void> {
        return feachTokenSubject.asObservable()
    }
    
    var requestLogin: Observable<URLRequest> {
        return loginRequestTrigger.asObservable()
    }
    
    private let bag = DisposeBag()
    
    init(api: APIClient, service: UserDefaultsService) {
        
        guard let url = URL(string: githubURL) else { return }
        
        viewWillAppear.asObservable()
            .map { _ in URLRequest(url: url) }
            .bind(to: loginRequestTrigger)
            .disposed(by: bag)

        let token = requestAccessToken.asObservable()
            .flatMapLatest { api.response(from: AccessTokenRequest(code: $0)) }
            .asObservable()
        
        token.subscribe(onNext: { [weak self] token in
            service.setToken(token)
            self?.feachTokenSubject.on(.next(()))
        }).disposed(by: bag)
    }
}
