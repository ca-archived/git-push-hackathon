//
//  MenuViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/31.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class MenuViewController: UIViewController {
    
    @IBOutlet weak private var avatarImageView: UIImageView!
    @IBOutlet weak private var name: UILabel!
    @IBOutlet weak private var menuTableView: UITableView!
    
    @IBAction private func completionButtonTapped(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    private var menuVM = MenuViewModel()
    static func instatiate(user: User) -> MenuViewController {
        let storyboard = UIStoryboard(name: "MenuViewController", bundle: nil)
        let menuVC = storyboard.instantiateInitialViewController() as! MenuViewController
        menuVC.menuVM = MenuViewModel.instantiate(user: user)
        return menuVC
    }
    
    private func initialize() {
        avatarImageView.layer.cornerRadius = avatarImageView.frame.size.width * 0.5
        avatarImageView.layer.masksToBounds = true
        self.setImage(imageView: self.avatarImageView, urlString: self.menuVM.user.avatar_url)
        self.name.text = menuVM.user.name
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initialize()
        menuTableView.dataSource = menuVM
        menuTableView.delegate = self
        menuTableView.tableFooterView = UIView(frame: .zero)
    }
}

extension MenuViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}
