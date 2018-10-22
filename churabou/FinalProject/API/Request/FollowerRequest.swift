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
    
//    var parameters: [String : String] {
//        return ["page": "\(page)"]
//    }
    
    typealias ResponseType = [User]
    
    //    func response(from data: Data) -> String? {
    //
    //        let s = try? JSONDecoder().decode(AccesToken.self, from: data)
    //        print(s)
    //        print(String(data: data, encoding: .utf8))
    //        if let token = String(data: data, encoding: .utf8)?
    //            .split(separator: "&").first?
    //            .split(separator: "=").last
    //            .map(String.init)
    //        {
    //            print(token)
    //            return token
    //        }
    //        return nil
    //    }
}
