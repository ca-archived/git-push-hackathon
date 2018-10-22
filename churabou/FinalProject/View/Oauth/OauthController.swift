//
//  Controller.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/09.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import WebKit
import RxSwift
import RxCocoa

final class OauthController: BaseController {
   
    private let viewModel: OauthViewModel
    private var dataSource: OauthViewDataSource
    
    init(viewModel: OauthViewModel, dataSource: OauthViewDataSource) {
        self.viewModel = viewModel
        self.dataSource = dataSource
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private var baseView = OauthView()
    private var webView: WKWebView { return baseView.webView }
    
    override func loadView() {
        view = baseView
    }

    private var bag = DisposeBag()
    
    override func commonInit() {
        addLeftBarButtonToClose()
        webView.navigationDelegate = dataSource
    }
    
    override func initializeView() {
        
        rx.sentMessage(#selector(viewWillAppear(_:)))
            .take(1).map { _ in }
            .bind(to: viewModel.viewWillAppear)
            .disposed(by: bag)
        
        viewModel.requestLogin.subscribe(onNext: { [weak self] request in
            self?.webView.load(request)
        }).disposed(by: bag)
        
        viewModel.feachToken.subscribe(onNext: { [weak self] _ in
            self?.dismiss(animated: true, completion: nil)
        }).disposed(by: bag)
    }
}
