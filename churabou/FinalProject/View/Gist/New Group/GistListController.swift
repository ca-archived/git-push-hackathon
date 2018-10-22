//
//  GistListController.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/10.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import RxSwift
import ReactorKit

final class GistListController: BaseController, View {
    
    private var router: GistListRouter
    
    init(reactor: GistListReactor, router: GistListRouter) {
        self.router = router
        super.init(nibName: nil, bundle: nil)
        self.reactor = reactor
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private var baseView = GistListView()
    private var tableView: UITableView { return baseView.tableView }
    private var indicator: UIActivityIndicatorView { return baseView.indicator}
    private var refresh: UIRefreshControl { return baseView.refresh}
    
    override func loadView() {
        view = baseView
    }
    override func initializeView() {
        title = "タイムライン"
        reactor = GistListReactor(session: GistSession())
    }
    
    override func viewWillAppear() {
        navigationController?.navigationBar.isTranslucent = false
    }
    
    var disposeBag = DisposeBag()
    
    func bind(reactor: GistListReactor) {
        
        rx.sentMessage(#selector(viewWillAppear(_:)))
            .take(1)
            .map { _ in Reactor.Action.load }
            .bind(to: reactor.action)
            .disposed(by: disposeBag)
        
        rx.sentMessage(#selector(viewDidDisappear(_:)))
            .map { _ in Reactor.Action.viewDidDisappear }
            .bind(to: reactor.action)
            .disposed(by: disposeBag)
        
        refresh.rx.controlEvent(.valueChanged)
            .map { _ in Reactor.Action.load }
            .bind(to: reactor.action)
            .disposed(by: disposeBag)
        
        tableView.rx.itemSelected
            .map { Reactor.Action.selectGist($0)}
            .bind(to: reactor.action)
            .disposed(by: disposeBag)
        
        tableView.rx.reachedBottom.skip(1)
            .map { _ in Reactor.Action.loadMore }
            .bind(to: reactor.action)
            .disposed(by: disposeBag)
        
        reactor.state.map { $0.isLoading }
            .bind(to: indicator.rx.isAnimating)
            .disposed(by: disposeBag)
        
        reactor.state.map { $0.gists }
            .distinctUntilChanged()
            .bind(to: tableView.rx.items(cellIdentifier: "cell")) { indexPath, gist, cell in
                guard let cell = cell as? GistTableViewCell else {
                    return
                }
                cell.configure(gist: gist)
            }
            .disposed(by: disposeBag)
        
        reactor.state.map { $0.isRefreshing }
            .bind(to: refresh.rx.isRefreshing)
            .disposed(by: disposeBag)
        
        reactor.state.map { $0.selectedGist }
            .filter { $0 != nil }
            .distinctUntilChanged()
            .subscribe(onNext: { [weak self] gist in
                guard let `self` = self, let gist = gist else {
                    return
                }
                self.router.showGist(gist: gist)
            })
            .disposed(by: disposeBag)
    }
}
