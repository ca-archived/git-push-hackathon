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

    var presenter: LoginViewPresenterProtocol!
    var model: LoginViewModel = LoginViewModel()

    // MARK: UIViewController

    override func awakeFromNib() {

        super.awakeFromNib()

        LoginContainer.shared.configure(self)
    }

    override func viewDidLoad() {

        super.viewDidLoad()

        setup()

        presenter.load()
    }

    // MARK: Private

    @IBOutlet private weak var loginButton: UIButton!

    private let disposeBag = DisposeBag()
    private var oauthKey: String?

    private func setup() {

        presenter.logInData.subscribe { [unowned self] event in

            switch event {
            case .next(let value):
                self.model = value
            case .error(let error):
                print(error.localizedDescription)
            case .completed:
                break
            }
        }.disposed(by: disposeBag)

        loginButton.rx.tap.subscribe { [unowned self] _ in

            self.model.authObservable.subscribe { event in

                switch event {
                case .next(let value):
                    self.oauthKey = value.oauthToken
                    // 遷移する
                default:
                    break
                }
            }.disposed(by: self.disposeBag)
        }.disposed(by: disposeBag)
    }
}
