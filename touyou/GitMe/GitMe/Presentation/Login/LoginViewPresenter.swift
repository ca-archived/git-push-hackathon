//
//  LoginViewPresenter.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/22.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation
import RxSwift

protocol LoginPresenterProtocol {

    var logInData: PublishSubject<UserInfoViewModel> { get }

    func logIn()
}

class LoginPresenter {

    init(converter: LoginConverterProtocol) {

        self.converter = converter
    }

    // MARK: Private

    private let converter: LoginConverterProtocol!
    private let disposeBag = DisposeBag()
    private(set) var logInData = PublishSubject<UserInfoViewModel>()
}

// MARK: - Protocol Interface

extension LoginPresenter: LoginPresenterProtocol {

    func logIn() {

        converter.logIn()
            .observeOn(MainScheduler.instance)
            .subscribe { [weak self] event in

                guard let `self` = self else { return }

                switch event {
                case .next(let value):
                    self.logInData.onNext(value)
                default:
                    break
                }
            }.disposed(by: disposeBag)
    }
}

