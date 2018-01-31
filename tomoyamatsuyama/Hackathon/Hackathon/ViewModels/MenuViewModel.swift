//
//  MenuViewModel.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/31.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation
import UIKit

class MenuViewModel: NSObject, UITableViewDataSource {
    
    private var user = User()
    private var cellItems: [String] = ["Events", "Organaizations", "Repositories", "Gists"]
    
    static func instantiate(user: User) -> MenuViewModel {
        let menuVM = MenuViewModel()
        menuVM.user = user
        return menuVM
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return 2
        } else {
            return cellItems.count
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.section == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "followDataCell") as! FollowDataTableViewCell
            if indexPath.row == 0 {
                cell.bind(cell, name: "following", value: String(user.following))
            } else {
                cell.bind(cell, name: "followers", value: String(user.followers))
            }
            return cell
        } else {
            let cell = tableView.dequeueReusableCell(withIdentifier: "userDataCell") as! UITableViewCell
            cell.textLabel!.text = cellItems[indexPath.row]
            return cell
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
}
