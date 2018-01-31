//
//  HomeViewModel.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation

import UIKit
import Alamofire

class HomeViewModel: NSObject, UITableViewDataSource {
    private var events: Array<Events> = Array<Events>()
    
    func setUserData(dic: Dictionary<String, Any>) -> User? {
        guard let avatarUrl: String = dic["avatar_url"] as? String else { return nil}
        guard let name: String = dic["name"] as? String else { return nil}
        guard let publicRepos: Int = dic["public_repos"] as? Int else { return nil}
        guard let reposUrl: String = dic["repos_url"] as? String else { return nil}
        guard let starredUrl: String = dic["starred_url"] as? String else { return nil}
        guard let bio: String = dic["bio"] as? String else { return nil}
        guard let followingUrl: String = dic["following_url"] as? String else { return nil}
        guard let followersUrl: String = dic["followers_url"] as? String else { return nil}
        guard let followers: Int = dic["followers"] as? Int else { return nil}
        guard let following: Int = dic["following"] as? Int else { return nil}
        guard let id: Int = dic["id"] as? Int else { return nil}
        guard let login: String = dic["login"] as? String else { return nil}
        
        let user = User(public_repos: publicRepos, repos_url: reposUrl, starred_url: starredUrl, bio: bio, following_url: followingUrl, followers_url: followersUrl, id: id, followers: followers, following: following, name: name, avatar_url: avatarUrl, login: login)
        return user
    }
    
    func requestUserData(completion: ((User) -> Void)? = nil) {
        guard let oauth = UserDefaults.standard.string(forKey: "access_token") else { return }
        let url = "https://api.github.com/user?access_token=\(oauth)"
        Alamofire.request(url).responseJSON { response in
            if let dic = response.result.value as? Dictionary<String, Any> {
                let userData = self.setUserData(dic: dic)
                guard let user = userData else { return }
                completion?(user)
            }
        }
    }
    
    func requestEvents(userName: String, completion: (() -> Void)? = nil) {
        let url = "https://api.github.com/users/\(userName)/received_events"
        print(url)
        Alamofire.request(url).response { response in
            if let data = response.data {
                self.events = try! JSONDecoder().decode(Array<Events>.self, from: data)
                completion?()
            }
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.events.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "homeCell", for: indexPath) as! HomeTableViewCell
        if events.count > 0 {
            cell.bind(cell, event: events[indexPath.row])
        }
        return cell
    }
}
