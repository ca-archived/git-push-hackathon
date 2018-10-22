//
//  AccessTokenRequest.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/10.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Alamofire

struct AccessTokenRequest: GitHubRequest {

    private var code: String

    init(code: String) {
        self.code = code
    }

    var parameters: Parameters {
        return [
            "client_id": AppConfig.clientId,
            "client_secret": AppConfig.clientSecret,
            "code": code
        ]
    }
    
    var path: String {
        return "login/oauth/access_token"
    }
    
    var headers: HTTPHeaders {
        return ["ACCEPT": "application/json"]
    }
    
    typealias ResponseType = AccesToken

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
    
    
    var baseURL: String {
        return "https://github.com/"
    }
}
