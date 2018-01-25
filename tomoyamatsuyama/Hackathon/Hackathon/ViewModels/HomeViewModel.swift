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
    private var feedList: [Feeds] = [Feeds()]
    
    func requestFeed(comletion: (() -> Void)? = nil){
        guard let oauth = UserDefaults.standard.string(forKey: "access_token") else { return }
        let url = "https://api.github.com/feeds"
        let param = ["access_token": oauth]
        Alamofire.request(url, parameters: param).responseJSON { response in
            if let dic = response.result.value as? Dictionary<String, Any>{
            guard let timelineUrl: String = dic["timeline_url"] as? String else { return }
            let parse = XmlParse()
            let feed = parse.parseXML(timelineUrl)
            guard let fed = feed else { return }
            self.feedList = fed
            comletion?()
            }
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.feedList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "homeCell", for: indexPath) as! HomeTableViewCell
        cell.bind(cell, avatarUrl: feedList[indexPath.row].avatarUrlString, title: feedList[indexPath.row].title)
        return cell
    }
}
