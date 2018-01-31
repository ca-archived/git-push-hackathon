//
//  HomeViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class HomeViewController: UIViewController {
    
    @IBOutlet weak var homeTableView: UITableView!
    private let homeVM = HomeViewModel()
    private var user = User()
    private let indicator = UIActivityIndicatorView()
    @IBOutlet weak private var avatarImageButton: UIButton!
    
    @IBAction private func avatarImageButtonTapped(_ sender: Any) {
        let menuVC = MenuViewController.instatiate(user: self.user)
        self.present(menuVC, animated: true, completion: nil)
    }
    
    private func getUserData() {
        homeVM.requestUserData(completion: { user in
            let image = self.imageSet(avatarUrl: user.avatar_url)
            self.user = user
            self.avatarImageButton.setBackgroundImage(image, for: .normal)
            
        })
        
    }
    
    private func imageSet(avatarUrl: String) -> UIImage? {
        avatarImageButton.layer.cornerRadius = avatarImageButton.frame.size.width * 0.5
        avatarImageButton.layer.masksToBounds = true
        avatarImageButton.imageView?.contentMode = .scaleAspectFit
        avatarImageButton.contentHorizontalAlignment = .fill
        avatarImageButton.contentVerticalAlignment = .fill
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
    
    static func instatiate() -> HomeViewController {
        let storyboard = UIStoryboard(name: "HomeViewController", bundle: nil)
        let homeVC = storyboard.instantiateInitialViewController() as! HomeViewController
        return homeVC
    }
    
    private func getFeed(){
        showIndicator(indicator: indicator)
        homeVM.requestFeed(comletion: { [weak self] in
            guard let `self` = self else { return }
            self.homeTableView.reloadData()
            self.stopIndecator(indicator: self.indicator)
        })
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.getUserData()
        self.getFeed()
        self.homeTableView.dataSource = homeVM
        self.homeTableView.delegate = self
    }
}

extension HomeViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}
