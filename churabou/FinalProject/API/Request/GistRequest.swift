//
//  GistRequest.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/10.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation
import Alamofire

enum GithubGistRequest {}


extension GithubGistRequest {
    
    struct Get: GitHubRequest {
        
        typealias ResponseType = [Gist]
        
        enum Target {
            case user(String), `public`
        }
        
        private var target: Target
        private var page: Int
        
        init(target: Target, page: Int = 1) {
            self.target = target
            self.page = page
        }
        
        var method: HTTPMethod {
            return .get
        }
        
        var path: String {
            
            switch target {
            case .public:
                return "/gists/public" + "?page=\(page)"
            case .user(let user):
                return "/users/\(user)/gists" + "?page=\(page)"
            }
        }
    }
}


extension GithubGistRequest {

    struct Create: GitHubRequest {
        
        typealias ResponseType = Gist
        
        var path: String {
            return "/gists"
        }
        
        private var files: [String: [String: String]]
        private var description: String
        
        init(description: String, files: [String: [String: String]]) {
            self.description = description
            self.files = files
        }
        
        var parameters: Parameters {
            return [
                "description" : description,
                "public" : true,
                "files": files
            ]
        }
    }
}

extension GithubGistRequest {
    
    struct Delete: GitHubRequest {
        
        private var id: String
        
        init(id: String) {
            self.id = id
        }
        
        var method: HTTPMethod {
            return .delete
        }
        
        var path: String {
            return "/gists/\(id)"
        }
        
        func response(from data: Data) -> Bool? {
            return true
        }
    }
}
