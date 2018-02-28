//
//  SettingsViewController.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/25.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - SettingsViewController

class SettingsViewController: UIViewController {

    // MARK: Internal

    var presenter: SettingsPresenterProtocol!

    // MARK: Life Cycle

    override func awakeFromNib() {

        super.awakeFromNib()

        SettingsContainer.shared.configure(self)
    }

    override func viewDidLoad() {

        super.viewDidLoad()
        self.navigationController?.setupBarColor()
    }

    // MARK: Private
    @IBOutlet private weak var tableView: UITableView! {

        didSet {

            tableView.register(UserInfoTableViewCell.self)
            tableView.dataSource = presenter
            tableView.delegate = self
            presenter.fetchUser {

                self.tableView.reloadData()
            }
        }
    }
}

// MARK: - TableView Delegate

extension SettingsViewController: UITableViewDelegate {

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {

        if indexPath.section == 1 {
            
            UIAlertController(title: "ログアウトしますか？", message: "", preferredStyle: .alert)
                .addAction(title: "OK", style: .default, handler: { _ in

                    self.presenter.logOut()
                    (self.tabBarController?.firstViewController as? MainViewController)?.logOut()
                    TabBarController.router.openLogInView()
                })
                .addAction(title: "Cancel", style: .cancel).show()
        }
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

// MARK: - Storyboard Instantiable

extension SettingsViewController: StoryboardInstantiable {}
