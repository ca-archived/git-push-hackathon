//
//  HomeViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa

class HomeViewController: UIViewController {
    
    static var storyboardName: String = "HomeViewController"
    private var homeVM = HomeViewModel()
    private let refreshControl = UIRefreshControl()
    private let indicator = UIActivityIndicatorView()
    private let disposeBag = DisposeBag()
    @IBOutlet weak var homeTableView: UITableView!
    @IBOutlet weak private var avatarImageButton: UIButton!
    
    @IBAction private func avatarImageButtonTapped(_ sender: Any) {
        let menuVC = MenuViewController.instatiate(user: homeVM.user)
        present(menuVC, animated: true, completion: nil)
    }
    
    static func instatiate(user: User) -> HomeViewController {
        let storyboard = UIStoryboard(name: storyboardName, bundle: nil)
        let homeVC = storyboard.instantiateInitialViewController() as! HomeViewController
        homeVC.homeVM = HomeViewModel.instantiate(user: user)
        return homeVC
    }
    
    private func bindViewModel() {
        self.homeVM.events.asDriver()
            .drive(homeTableView.rx.items(cellIdentifier: "homeCell", cellType: HomeTableViewCell.self)) { _, event, cell in
                cell.bind(cell, event: event)
            }.disposed(by: disposeBag)
    }
    
    private func setTableView() {
        homeTableView.refreshControl = refreshControl
        homeTableView.delegate = self
        refreshControl.addTarget(self, action: #selector(self.refreshControlValueChanged(sender:)), for: .valueChanged)
    }
    
    @objc func refreshControlValueChanged(sender: UIRefreshControl) {
        homeVM.reloadTableView()
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            sender.endRefreshing()
        }
    }
    
    private func configure() {
        avatarImageButton.translatesAutoresizingMaskIntoConstraints = false
        avatarImageButton.layer.cornerRadius = avatarImageButton.frame.size.width * 0.5
        avatarImageButton.layer.masksToBounds = true
        avatarImageButton.imageView?.contentMode = .scaleAspectFit
        avatarImageButton.contentHorizontalAlignment = .fill
        avatarImageButton.contentVerticalAlignment = .fill
        avatarImageButton.setBackgroundImage(imageSet(avatarUrl: homeVM.user.avatar_url), for: .normal)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configure()
        setTableView()
        bindViewModel()
        homeVM.reloadTableView()
    }
}

extension HomeViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}
