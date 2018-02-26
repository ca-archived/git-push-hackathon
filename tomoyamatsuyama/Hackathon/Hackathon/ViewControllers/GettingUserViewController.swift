//
//  GettingUserViewController.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit
import RxSwift

class GettingUserViewController: UIViewController {
    static var storyboardName: String = "GettingUserViewController"
    @IBOutlet private weak var userIconImageView: UIImageView!
    @IBOutlet private weak var loginDescription: UILabel!
    private var disposeBag = DisposeBag()
    private let gettingUserVM = GettingUserViewModel()
    
    @objc private func goToHomeVC(){
        let homeVC = HomeViewController.instatiate(user: gettingUserVM.user.value)
        self.navigationController?.pushViewController(homeVC, animated: true)
    }
    
    private func bindUserData() {
        gettingUserVM.user.asObservable()
            .subscribeOn(MainScheduler.instance)
            .subscribe(
                {_ in
                    if !self.gettingUserVM.user.value.avatar_url.isEmpty && !self.gettingUserVM.user.value.login.isEmpty {
                        self.setImage(imageView: self.userIconImageView, urlString: self.gettingUserVM.user.value.avatar_url)
                        self.loginDescription.text! += self.gettingUserVM.user.value.login
                        self.goToHomeVC()
                    }
                }
            )
            .disposed(by: disposeBag)
    }
    
    static func instantiate() -> UINavigationController {
        let storyboard = UIStoryboard(name: storyboardName, bundle: nil)
        guard let vc = storyboard.instantiateInitialViewController() as? UINavigationController else { fatalError("error") }
        return vc
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.userIconImageView.layer.cornerRadius = self.userIconImageView.frame.size.width * 0.5
        self.userIconImageView.layer.masksToBounds = true
        self.userIconImageView.image = UIImage(named: "defaultIcon")
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(true)
        self.gettingUserVM.requestUserData()
        self.bindUserData()
    }
}
