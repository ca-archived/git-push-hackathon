//
//  Event.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/31.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

struct Event: Codable {
    var actor: Actors
    var created_at: String
    var id: String
    var repo: Repos
    var type: String
    var payload: Payload?
    
    struct Actors: Codable {
        var avatar_url: String
        var display_login: String
        var gravatar_id: String
        var id: Int
        var login: String
        var url: String
    }
    
    struct Repos: Codable {
        var id: Int
        var name: String
        var url: String
    }
    
    struct Payload: Codable {
        var forkee: Forkee?
        var member: Member?
        
        struct Forkee: Codable {
            var id: Int?
            var name: String?
            var full_name: String?
        }
        
        struct Member: Codable {
            var login: String
        }
    }
}
