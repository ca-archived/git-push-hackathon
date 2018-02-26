//
//  LoginViewModel.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/26.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation
import UIKit
import RxSwift

class LoginViewModel: NSObject {
    private(set) var isStatus: Variable<Bool>  = .init(false)
    private var disposeBag = DisposeBag()
    
    func getCodeFromCallBackUrl(callBackUrl: URL) {
        guard let components = URLComponents(url: callBackUrl, resolvingAgainstBaseURL: false) else { return }
        guard let items = components.queryItems else { return }
        for item in items {
            if item.name == "code" {
                guard let code = item.value else { return }
                self.bind(code: code)
                break
            }
        }
    }
    
    private func bind(code: String) {
        Api.Oauth.requestToken(code: code)
            .subscribeOn(SerialDispatchQueueScheduler(qos: .background))
            .subscribe(
                onNext: { [weak self] Authentication in
                    guard let `self` = self else { return }
                    print(Authentication.accessToken)
                    ApiInfomation.set(key: .acccessToken, value: Authentication.accessToken)
                    self.isStatus.value = true
                },
                onCompleted: { () in
                    
            }
            )
            .disposed(by: disposeBag)
        
    }
}

