//
//  LoginUserViewModel.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/19.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import RxSwift

final class LoginUserViewModel {
    
    private let bag = DisposeBag()
    
    init(session: LoginUserRepository) {

        viewWillAppearstream
            .flatMapLatest{ session.fetchUser() }
            .bind(to: userSubject)
            .disposed(by: bag)
        
        tapDoneStream
            .withLatestFrom(userSubject)
            .bind(to: showHomeTrigger)
            .disposed(by: bag)
    }
    
    private var viewWillAppearstream = PublishSubject<Void>()
    private var tapDoneStream = PublishSubject<Void>()
    
    private var userSubject = BehaviorSubject(value: User())
    private var showHomeTrigger = PublishSubject<User>()
    
    var viewWillAppear: AnyObserver<Void> {
        return viewWillAppearstream.asObserver()
    }
    
    var tapDone: AnyObserver<Void> {
        return tapDoneStream.asObserver()
    }
    
    var user: Observable<User> {
        return userSubject.asObserver()
    }
    
    var showHome: Observable<User> {
        return showHomeTrigger.asObservable()
    }
}
