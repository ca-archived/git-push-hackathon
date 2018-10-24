//
//  LoginUserSession.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/20.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import RxSwift
import RxCocoa

protocol LoginUserRepository {
    func fetchUser() -> Observable<User>
}


struct LoginUserSession: LoginUserRepository {
    
    private let api = APIClient()
    
    func fetchUser() -> Observable<User> {
        let request = LoginUserRequest()
        return api.response(from: request).catchErrorJustReturn(.init())
    }
}
