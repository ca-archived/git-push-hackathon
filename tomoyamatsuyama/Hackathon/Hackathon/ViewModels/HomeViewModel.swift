//
//  HomeViewModel.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//
import Foundation
import UIKit
import RxSwift
import RxCocoa

class HomeViewModel: NSObject {
    private(set) var user: User = .init()
    private(set) var events: Variable<[Event]> = .init([])
    private let users = Api.Users()
    private let disposeBag = DisposeBag()
    
    static func instantiate(user: User) -> HomeViewModel {
        let homeVM = HomeViewModel()
        homeVM.user = user
        return homeVM
    }
    
    func reloadTableView() {
        Api.Users.getEvents(name: user.login)
            .subscribe(
                onNext: { [weak self] events in
                    guard let `self` = self else { return }
                    self.events.value = events
                }
            )
            .disposed(by: disposeBag)
    }
}
