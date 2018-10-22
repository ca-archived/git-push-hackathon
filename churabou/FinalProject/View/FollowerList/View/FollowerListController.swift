//
//  FollowerListController.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import Nuke
import ReactorKit
import RxSwift

final class FollowerListController: BaseController, View {
    
    private var router: FollowerListRouter

    init(reactor: FollowerListReactor, router: FollowerListRouter) {
        self.router = router
        super.init(nibName: nil, bundle: nil)
        self.reactor = reactor
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private var baseView = FollowerListView()
    private var tableView: UITableView { return baseView.tableView}
    private var indicator: UIActivityIndicatorView { return baseView.indicator }
    
    override func loadView() {
        view = baseView
    }
    
    override func viewWillAppear() {
        navigationController?.isNavigationBarHidden = false
        title = "follower"
    }
    
    var disposeBag = DisposeBag()

    func bind(reactor: FollowerListReactor) {
        
        rx.sentMessage(#selector(viewWillAppear(_:)))
            .map { _ in Reactor.Action.load }
            .bind(to: reactor.action)
            .disposed(by: disposeBag)
        
        tableView.rx.reachedBottom
            .map { _ in Reactor.Action.loadMore }
            .bind(to: reactor.action)
            .disposed(by: disposeBag)
        
        tableView.rx.itemSelected
            .map { Reactor.Action.selectUser($0) }
            .bind(to: reactor.action)
            .disposed(by: disposeBag)
        
        reactor.state.map { $0.isLoading }
            .bind(to: indicator.rx.isAnimating)
            .disposed(by: disposeBag)

        reactor.state.map { $0.users }
            .bind(to: tableView.rx.items(cellIdentifier: "cell")) { indexPath, user, cell in
                guard let cell = cell as? FollowerCell else {
                    return
                }
                cell.configure(user: user)
            }
            .disposed(by: disposeBag)
        
        reactor.state.map { $0.selectedUser }
            .filter { $0 != nil }
//            .distinctUntilChanged { $0?.name ==  $0?.name }
            .subscribe(onNext: { [weak self] user in
                guard let `self` = self, let user = user else {
                    return
                }
                self.router.showUser(user)
            }).disposed(by: disposeBag)
    }
}
