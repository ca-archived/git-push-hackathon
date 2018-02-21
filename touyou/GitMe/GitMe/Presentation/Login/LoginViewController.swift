//
//  LoginViewController.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/22.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa

// MARK: - LoginViewController

class LoginViewController: UIViewController {

    // MARK: Internal

    var presenter: LoginPresenterProtocol!

    // MARK: UIViewController

    override func awakeFromNib() {

        super.awakeFromNib()

        LoginContainer.shared.configure(self)
    }

    override func viewDidLoad() {

        super.viewDidLoad()

        setup()
    }

    // MARK: Private

    @IBOutlet private weak var loginButton: UIButton!

    private let disposeBag = DisposeBag()
    private var oauthKey: String?

    private func setup() {

        loginButton.rx.tap.subscribe { [unowned self] _ in

            self.presenter.logIn()
        }.disposed(by: disposeBag)

        presenter.logInData
            .observeOn(MainScheduler.instance)
            .subscribe { [unowned self] event in

                switch event {
                case .next(let value):
                    TabBarController.router.openLoadingWindow(userInfo: value)
                    self.dismiss(animated: true, completion: nil)
                case .error(let error):
                    print(error)
                case .completed:
                    break
                }
        }.disposed(by: disposeBag)
    }
}

// MARK: - Storyboard Instantiable

extension LoginViewController: StoryboardInstantiable {}
