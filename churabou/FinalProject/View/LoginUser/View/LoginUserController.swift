//
//  LoginUserController.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/10.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import RxSwift
import Nuke

final class LoginUserController: BaseController {
    
    private var viewModel: LoginUserViewModel
    private var router: LoginUserRouter

    init(viewModel: LoginUserViewModel, router: LoginUserRouter) {
        self.viewModel = viewModel
        self.router = router
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private let baseView = LoginUserView()
    private var popUp: LoginUserPopUp { return baseView.popUp }
    private var doneButton: UIButton { return baseView.popUp.doneButton }
    private let bag = DisposeBag()
    
    override func commonInit() {
        modalPresentationStyle = .overCurrentContext
    }
    
    override func loadView() {
        view = baseView
    }
    
    override func initializeView() {
        
        rx.sentMessage(#selector(viewWillAppear(_:)))
            .take(1).map { _ in }
            .bind(to: viewModel.viewWillAppear)
            .disposed(by: bag)
        
        doneButton.rx.tap
            .bind(to: viewModel.tapDone)
            .disposed(by: bag)
        
        viewModel.user.skip(1).subscribe(onNext: { [weak self] user in
            if user.name.isEmpty {
                SimplePopUp.show(title: "通信環境を確認して再起動")
            } else {
                self?.popUp.show(user: user)
            }
        }).disposed(by: bag)
        
        viewModel.showHome.subscribe(onNext: { [weak self] user in
            self?.router.showHome(user: user)
        }).disposed(by: bag)
    }
}
