//
//  HomeViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

class HomeViewController: UIViewController{
    
    @IBOutlet weak var homeTableView: UITableView!
    private let homeVM = HomeViewModel()
    
    static func instatiate() -> HomeViewController {
        let storyboard = UIStoryboard(name: "HomeViewController", bundle: nil)
        let homeVC = storyboard.instantiateInitialViewController() as! HomeViewController
        return homeVC
    }
    
    func getFeed(){
        homeVM.requestFeed(comletion: { [weak self] in
            guard let `self` = self else { return }
            self.homeTableView.reloadData()
        })
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.getFeed()
        self.homeTableView.dataSource = homeVM
    }
}
