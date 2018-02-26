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
    
    private func goToHomeVC(){
        let homeVC = HomeViewController.instatiate(user: gettingUserVM.user.value)
        self.navigationController?.pushViewController(homeVC, animated: true)
    }
    
    private func goToLoginVC() {
        let loginVC = LoginViewController.instantiate()
        self.navigationController?.present(loginVC, animated: true, completion: nil)
    }
    
    private func accessTokenIsNil() {
        if ApiInfomation.get(key: .acccessToken) != nil {
            self.gettingUserVM.requestUserData()
            self.bindUserData()
        } else {
            self.goToLoginVC()
        }
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
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.accessTokenIsNil()
        
    }
}
