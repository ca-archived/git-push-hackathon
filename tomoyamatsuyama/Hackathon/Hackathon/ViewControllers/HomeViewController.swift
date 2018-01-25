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
    
    static func instatiate() -> HomeViewController {
        let storyboard = UIStoryboard(name: "HomeViewController", bundle: nil)
        let homeVC = storyboard.instantiateInitialViewController() as! HomeViewController
        return homeVC
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
