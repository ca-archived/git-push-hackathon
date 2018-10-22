//
//  Home.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/19.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

final class HomeTabController: UITabBarController {
    
    private var user: User = .mock()

    init(user: User) {
        print("init")
         self.user = user
        super.init(nibName: nil, bundle: nil)
         print("init user")
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
         print("didload")
        
        let timeLineC = AppRouter.makeGistListController()
        let timeLineN = UINavigationController(rootViewController: timeLineC)
        timeLineN.tabBarItem = UITabBarItem.init(title: "タイムライン", image: nil, tag: 1)
      
        let userC = AppRouter.makeUserGistController(user: user)
        let userN = UINavigationController(rootViewController: userC)
        userN.tabBarItem = UITabBarItem.init(title: "ユーザー", image: nil, tag: 1)
        
        setViewControllers([timeLineN, userN], animated: false)
        
        
        tabBar.tintColor = .orange
        tabBar.items?[0].titlePositionAdjustment = UIOffset(horizontal: -15, vertical: 0)
        tabBar.items?[1].titlePositionAdjustment = UIOffset(horizontal: 15, vertical: 0)
        
        view.addSubview(postButton)

        postButton.snp.makeConstraints { make in
            make.size.equalTo(70)
            make.centerX.equalToSuperview()
            make.bottom.equalTo(view.safeAreaLayoutGuide.snp.bottom).offset(-5)
        }
    }
    
    private lazy var postButton = UIButton().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.setTitle("+", for: .normal)
        it.setTitleColor(.white, for: .normal)
        it.layer.cornerRadius = 35
        it.backgroundColor = .orange
        it.addTarget(self, action: #selector(actionPostButton), for: .touchUpInside)
    }
    
    @objc private func actionPostButton() {
        let create = CreateGistController()
        let createN = UINavigationController(rootViewController: create)
        present(createN, animated: true, completion: nil)
    }
}
