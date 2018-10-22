//
//  GitHubRequest.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/10.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Alamofire

protocol GitHubRequest: Request { }

extension GitHubRequest {
    
    var baseURL: String {
        return "https://api.github.com"
    }
    
    var method: HTTPMethod {
        return .post
    }

    var parameters: Parameters {
        return [:]
    }
    
    var headers: HTTPHeaders {
        var header: HTTPHeaders = ["Content-type": "application/json"]
        if !Config.token.isEmpty {
            header["Authorization"] = "token \(Config.token)"
        }
        return header
    }
}

