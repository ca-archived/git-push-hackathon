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
    
    
    private var user = User()
    private var menuVM = MenuViewModel()
    
    static func instatiate(user: User) -> MenuViewController {
        let storyboard = UIStoryboard(name: "MenuViewController", bundle: nil)
        let menuVC = storyboard.instantiateInitialViewController() as! MenuViewController
        menuVC.user = user
        menuVC.menuVM = MenuViewModel.instantiate(user: user)
        return menuVC
    }
    
    private func imageSet(avatarUrl: String) -> UIImage? {
        avatarImageView.layer.cornerRadius = avatarImageView.frame.size.width * 0.5
        avatarImageView.layer.masksToBounds = true
        guard let imageUrl = URL(string: avatarUrl) else { return nil }
        do {
            let imageData = try Data(contentsOf: imageUrl, options: Data.ReadingOptions.mappedIfSafe)
            guard let image = UIImage(data: imageData) else { return nil }
            return image
        } catch {
            print("Error: cant create image.")
            return nil
        }
    }
    
    private func initializeView() {
        self.avatarImageView.image = imageSet(avatarUrl: self.user.avatar_url)
        self.name.text = self.user.name
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.initializeView()
        self.menuTableView.dataSource = menuVM
        self.menuTableView.delegate = self
        self.menuTableView.tableFooterView = UIView(frame: .zero)
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
