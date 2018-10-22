//
//  LoginUserRequest.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/10.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Alamofire

struct LoginUserRequest: GitHubRequest {
    
    public typealias ResponseType = User
    
    public var method: HTTPMethod {
        return .get
    }
    
    public var path: String {
        return "/user"
    }
}
