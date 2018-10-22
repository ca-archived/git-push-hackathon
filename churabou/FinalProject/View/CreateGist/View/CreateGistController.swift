//
//  CreateGistController.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa

final class CreateGistController: BaseController {
    
    private var baseView = CreateGistView()
    private var tableView: UITableView { return baseView.tableView }
    private var addFileButton: UIButton { return baseView.addButton }
    private var descreptionField: UITextField { return baseView.descreptionField }
    private var postButton = UIBarButtonItem(title: "投稿", style: .plain, target: nil, action: nil)
    
    override func loadView() {
        view = baseView
    }
    
    private var disposeBag = DisposeBag()
    private let viewModel = CreateGistViewModel()

    override func viewWillAppear() {
        navigationController?.navigationBar.isTranslucent = false
    }
    
    override func commonInit() {
        tableView.delegate = self
        tableView.dataSource = self
        addLeftBarButtonToClose()
        navigationItem.rightBarButtonItem = postButton
    }

    override func initializeView() {
        
        descreptionField.rx.text.orEmpty.skip(1)
            .bind(to: viewModel.description)
            .disposed(by: disposeBag)
        
        postButton.rx.tap
            .debounce(0.3, scheduler: MainScheduler.instance)
//            .withLatestFrom(descreptionField.rx.text.orEmpty)
            .subscribe(onNext: { [weak self] in
                self?.viewModel.input.tapPost()
            }).disposed(by: disposeBag)
        
        addFileButton.rx.tap.asDriver().drive(onNext: { [weak self] in
            self?.viewModel.input.tapAdd()
        }).disposed(by: disposeBag)
        
        
        viewModel.output.tableViewRefresh.subscribe(onNext: { [weak self] type in
            switch type {
            case .delete(let rows):
                self?.tableView.deleteRows(at: rows.map { IndexPath(row: $0, section: 0) }, with:
                .automatic)
            case .insert(let rows):
                self?.tableView.insertRows(at: rows.map { IndexPath(row: $0, section: 0) }, with: .bottom)
                self?.tableView.scrollToRow(at:  IndexPath(row: rows[0], section: 0),
                                           at: .bottom,
                                           animated: true)
            }
        }).disposed(by: disposeBag)

        viewModel.output.uploadState.subscribe(onNext: { [weak self] state in
            switch state {
            case .success:
                self?.dismiss(animated: true, completion: nil)
            case .failure(let message):
                SimplePopUp.show(title: message)
            }
        }).disposed(by: disposeBag)
    }
}

extension CreateGistController: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.output.files.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! CreateGistCell
        cell.configure(store: viewModel.store, file: viewModel.files[indexPath.row])
        return cell
    }
}

extension CreateGistController: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return CreateGistCell.height
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        
        if editingStyle == .delete {
            viewModel.input.tapDelete(row: indexPath.row)
        }
    }
}
