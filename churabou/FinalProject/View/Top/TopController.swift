//
//  TopController.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/09.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa


final class TopController: BaseController {
    
    private let viewModel: TopViewModelType
    private let router: TopRouter
    
    init(viewModel: TopViewModelType, router: TopRouter) {
        self.viewModel = viewModel
        self.router = router
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private let bag = DisposeBag()
    private var baseView = TopView()
    private var loginButton: UIButton { return baseView.loginButton }
   

    override func loadView() {
        view = baseView
    }

    override func initializeView() {
        
        rx.sentMessage(#selector(viewDidAppear(_:)))
            .map { _ in }
            .bind(to: viewModel.input.viewWiiAppear)
            .disposed(by: bag)
            
        loginButton.rx.tap
            .bind(to: viewModel.input.tapLogin)
            .disposed(by: bag)
        
        viewModel.output.requestLogin
            .bind(to: showLoginController)
            .disposed(by: bag)
        
        viewModel.output.showLoginUser
            .bind(to: showLoginUser)
            .disposed(by: bag)
    }
    
    private var showLoginController: AnyObserver<Void> {
        return Binder(self) { c, _ in
            self.router.showOuth()
        }.asObserver()
    }
    
    private var showLoginUser: AnyObserver<Void> {
        return Binder(self) { c, _ in
            self.router.showLoginUser()
        }.asObserver()
    }
}

