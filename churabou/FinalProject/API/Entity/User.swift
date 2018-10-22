//
//  User.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation

struct User: Codable {
    
    var id = 0
    var name = ""
    var iconURL: URL?
    // userでは帰ってくるがgistのoutherには帰ってこない
    var followers: Int?
    var following: Int?
    
    enum CodingKeys: String, CodingKey {
        case id = "id"
        case name = "login"
        case iconURL = "avatar_url"
        case followers
        case following
    }
}


extension User {
    
    static func mockUsers() -> [User] {
        var user = User()
        user.iconURL = URL(string: "https://avatars1.githubusercontent.com/u/27997542?v=4")!
        user.name = "impleuser9"
        return (0...10).map { _ in user }
    }
    
    static func mock() -> User {
        var user = User()
        user.iconURL = URL(string: "https://avatars1.githubusercontent.com/u/27997542?v=4")!
        user.name = "churabou"
        return user
    }
}
