//
//  FollowerRequest.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Alamofire

struct FollowerRequest: GitHubRequest {
    
    private var username: String
    private var page: Int
    
    init(username: String, page: Int) {
        self.username = username
        self.page = page
    }
    
    var path: String {
        return "/users/\(username)/followers?page=\(page)"
    }
    
    var method: HTTPMethod {
        return .get
    }
    
    typealias ResponseType = [User]
}
