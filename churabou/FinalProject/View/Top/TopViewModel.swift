//
//  TopViewModel.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/09.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import RxSwift

protocol TopViewModelType {
    var input: TopViewInput { get }
    var output: TopViewOutput { get }
}

protocol TopViewInput {
    var tapLogin: AnyObserver<Void> { get }
    var viewWiiAppear: AnyObserver<Void> { get }
}

protocol TopViewOutput {
    var requestLogin: Observable<Void> { get }
    var showLoginUser: Observable<Void> { get }
}

final class TopViewModel: TopViewModelType {
    
    var input: TopViewInput { return self }
    var output: TopViewOutput { return self }

    private let bag = DisposeBag()
    private var tapLoginStream = PublishSubject<Void>()
    private var viewWillAppearStream = PublishSubject<Void>()
    private var requestLoginTrigger = PublishSubject<Void>()
    private var showUserTrigger = PublishSubject<Void>()
    
    init() {
    
        tapLoginStream
            .bind(to: requestLoginTrigger)
            .disposed(by: bag)
        
        viewWillAppearStream
            .filter { Config.isLogin }
            .bind(to: showUserTrigger)
            .disposed(by: bag)
    }
}

extension TopViewModel: TopViewInput {
    
    var tapLogin: AnyObserver<Void> {
        return tapLoginStream.asObserver()
    }
    
    var viewWiiAppear: AnyObserver<Void> {
        return viewWillAppearStream.asObserver()
    }
}

extension TopViewModel: TopViewOutput {
    
    var requestLogin: Observable<Void> {
        return requestLoginTrigger.asObservable()
    }
    
    var showLoginUser: Observable<Void> {
        return showUserTrigger.asObservable()
    }
}
