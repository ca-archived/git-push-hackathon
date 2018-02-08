//
//  LoginViewPresenter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/22.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation
import RxSwift

//protocol LoginViewPresenterProtocol {
//
//    var logInData: PublishSubject<LoginViewModel> { get }
//
//    func load()
//}
//
//class LoginViewPresenter {
//
//    init(useCase: LoginUseCaseProtocol) {
//
//        self.useCase = useCase
//    }
//
//    // MARK: Private
//
//    private let useCase: LoginUseCaseProtocol!
//    private let disposeBag = DisposeBag()
//    private(set) var logInData = PublishSubject<LoginViewModel>()
//}
//
//extension LoginViewPresenter: LoginViewPresenterProtocol {
//
//    func load() {
//
//        useCase.logIn()
//            .observeOn(MainScheduler.instance)
//            .subscribe { [weak self] event in
//
//                guard let `self` = self else { return }
//
//                switch event {
//                case .next(let value):
//                    self.logInData.onNext(value)
//                default:
//                    break
//                }
//            }.disposed(by: disposeBag)
//    }
//}

