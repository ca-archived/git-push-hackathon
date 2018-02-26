//
//  GettingUserViewModel.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation
import RxSwift

class GettingUserViewModel {
    private(set) var user: Variable<User> = .init(User())
    private(set) var isError: Variable<Bool> = .init(false)
    private var disposeBag = DisposeBag()
    
    
    func requestUserData(){
        Api.Users.getUser()
            .subscribeOn(SerialDispatchQueueScheduler(qos: .background))
            .subscribe(
                onNext: { [weak self] user in
                    guard let `self` = self else { return }
                    self.user.value = user
                },
                onError: { [weak self] error in
                    guard let `self` = self else { return }
                    self.isError.value = true
                }
            )
            .disposed(by: disposeBag)
    }
}
