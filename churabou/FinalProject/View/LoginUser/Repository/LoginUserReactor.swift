//
//  LoginUserReactor.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/20.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import RxSwift
import RxCocoa

//final class LoginUserReactor: Reactor {
//    
//    enum Action {
//        case viewWillAppear
//        case tapStart
//    }
//    
//    struct State {
//        var user: User?
//    }
//    
//    enum Mutation {
//        
//    }
//}

protocol LoginUserRepository {
    func fetchUser() -> Observable<User>
//    func saveUser()
}


struct LoginUserSession: LoginUserRepository {
    
    private let api = APIClient()
    
    func fetchUser() -> Observable<User> {
        let request = LoginUserRequest()
        return api.response(from: request).catchErrorJustReturn(.init())
    }
}
