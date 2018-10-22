//
//  UserGistController.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/13.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import RxCocoa
import RxSwift

final class UserGistController: BaseController {
    
    private var viewModel: UserGistViewModelType
    private var router: UserGistRouter
    init(user: User, viewModel: UserGistViewModel, router: UserGistRouter) {
        self.viewModel = viewModel
        self.router = router
        super.init(nibName: nil, bundle: nil)
        baseView.header.configure(user: user)
        title = user.name
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    private var baseView = UserGistView()
    private var tableView: UITableView { return baseView.tableView }
    private var refresh: UIRefreshControl { return baseView.refresh }
    private var indicator: UIActivityIndicatorView { return baseView.indicator }
    private var noGistView: UILabel { return baseView.noGistView }
    
    override func loadView() {
        view = baseView
    }
    
    override func commonInit() {
        tableView.dataSource = self
        tableView.delegate = self
    }
    
    override func viewWillAppear() {
        navigationController?.navigationBar.isTranslucent = false
        viewModel.input.viewWillAppear()
    }
    
    private let bag = DisposeBag()
    
    override func initializeView() {
        
        refresh.rx.controlEvent(.valueChanged)
            .subscribe(onNext: { [weak self] in
                self?.viewModel.input.refresh()
            }).disposed(by: bag)
        
        baseView.header.followerButton
            .rx.tap.asDriver().drive(onNext: { [weak self] in
                self?.viewModel.input.tapFollower()
            }).disposed(by: bag)
        
        viewModel.output.loadingGist
            .bind(to: refresh.rx.isRefreshing)
            .disposed(by: bag)
        
        viewModel.output.deletingGist
            .bind(to: indicator.rx.isAnimating)
            .disposed(by: bag)
        
        viewModel.output.tableViewUpdate.subscribe(onNext: { [weak self] type in
            switch type {
            case .reload:
                self?.tableView.reloadData()
            case .delete(let indexPath):
                self?.tableView.deleteRows(at: [indexPath], with: .automatic)
            }
        }).disposed(by: bag)
        
        viewModel.output.errorMessage.subscribe(onNext: { _ in
            SimplePopUp.show()
        }).disposed(by: bag)
        
        viewModel.output.gists.asObservable().skip(1)
            .map { !$0.isEmpty }
            .bind(to: noGistView.rx.isHidden)
            .disposed(by: bag)
        
        viewModel.output.showGist.subscribe(onNext: { [weak self] gist in
            self?.router.showGist(gist: gist)
        }).disposed(by: bag)
        
        viewModel.output.showFollower.subscribe(onNext: { [weak self] user in
            self?.router.showFollower(user: user)
        }).disposed(by: bag)
    }
}

extension UserGistController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.output.gists.value.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell") as! GistTableViewCell
        cell.configure(gist: viewModel.output.gists.value[indexPath.row])
        return cell
    }
}

extension UserGistController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return GistTableViewCell.height
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        
        if editingStyle == .delete {
            viewModel.input.requestDeleteGist(at: indexPath)
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        viewModel.input.selectGist(at: indexPath)
    }
}
