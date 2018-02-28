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
    private var disposeBag = DisposeBag()
    private let gettingUserVM = GettingUserViewModel()
    @IBOutlet private weak var userIconImageView: UIImageView!
    @IBOutlet private weak var loginDescription: UILabel!
    @IBOutlet private weak var reloadButton: UIButton!
    
    @IBAction func reloadButtonTapped(_ sender: Any) {
        gettingUserVM.isError.value = false
        gettingUserVM.requestUserData()
    }
    
    private func goToHomeVC(){
        let homeVC = HomeViewController.instatiate(user: gettingUserVM.user.value)
        navigationController?.pushViewController(homeVC, animated: true)
    }
    
    private func goToLoginVC() {
        let loginVC = LoginViewController.instantiate()
        navigationController?.present(loginVC, animated: true, completion: nil)
    }
    
    private func accessTokenIsNil() {
        if ApiInfomation.get(key: .acccessToken) != nil {
            gettingUserVM.requestUserData()
            bindUserData()
        } else {
            goToLoginVC()
        }
    }
    
    private func showAlert() {
        let alert = UIAlertController(title: "ネットワークエラー", message: "ネットワーク環境をご確認ください", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true, completion: nil)
    }
    
    private func bindUserData() {
        gettingUserVM.user.asObservable()
            .subscribeOn(MainScheduler.instance)
            .subscribe({ [weak self] user in
                guard let `self` = self else { return }
                if let user = user.element,
                    !user.avatar_url.isEmpty && !user.login.isEmpty {
                    self.setImage(imageView: self.userIconImageView, urlString: user.avatar_url)
                    self.loginDescription.text! += user.login
                    self.goToHomeVC()
                }
            })
            .disposed(by: disposeBag)
        
        gettingUserVM.isError.asObservable()
            .subscribeOn(MainScheduler.instance)
            .filter{ $0 }
            .subscribe({ [weak self] _ in
                guard let `self` = self else { return }
                self.showAlert()
            })
            .disposed(by: disposeBag)
        
        gettingUserVM.isError.asObservable()
            .map{ !$0 }
            .bind(to: reloadButton.rx.isHidden)
            .disposed(by: disposeBag)
    }
    
    static func instantiate() -> UINavigationController {
        let storyboard = UIStoryboard(name: storyboardName, bundle: nil)
        guard let vc = storyboard.instantiateInitialViewController() as? UINavigationController else { fatalError("error") }
        return vc
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        userIconImageView.layer.cornerRadius = userIconImageView.frame.size.width * 0.5
        userIconImageView.layer.masksToBounds = true
        userIconImageView.image = UIImage(named: "defaultIcon")
        reloadButton.isHidden = true
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        accessTokenIsNil()
    }
}
