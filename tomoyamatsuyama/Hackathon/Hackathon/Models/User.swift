//
//  User.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/31.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation

struct User: Codable {
    var public_repos: Int = 0
    var repos_url: String = ""
    var starred_url: String = ""
    var bio: String = ""
    var following_url: String = ""
    var followers_url: String = ""
    var id: Int = 0
    var followers: Int = 0
    var following: Int = 0
    var name: String = ""
    var avatar_url: String = ""
    var login: String = ""
}
