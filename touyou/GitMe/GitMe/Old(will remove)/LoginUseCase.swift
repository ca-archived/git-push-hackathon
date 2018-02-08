////
////  LoginUseCase.swift
////  GitMe
////
////  Created by 藤井陽介 on 2018/01/22.
////  Copyright © 2018 touyou. All rights reserved.
////
//
//import Foundation
//import RxSwift
//
//protocol LoginUseCaseProtocol {
//
//    func logIn() -> Observable<LoginViewModel>
//}
//
//class LoginUseCase {
//
//    init(dataStore: UserDataStoreProtocol) {
//
//        self.dataStore = dataStore
//    }
//
//    // MARK: Private
//
//    private let dataStore: UserDataStoreProtocol!
//
//    private func createLoginViewModel(_ isLoggedIn: Bool) -> LoginViewModel {
//
//        return LoginViewModel(isLoggedIn: isLoggedIn, authObservable: dataStore.fetchUserAuth())
//    }
//}
//
//extension LoginUseCase: LoginUseCaseProtocol {
//
//    func logIn() -> Observable<LoginViewModel> {
//
//        return dataStore.fetchLoginStatus()
//            .map { self.createLoginViewModel($0) }
//    }
//}

