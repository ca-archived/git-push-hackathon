//
//  Oauth.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/26.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation
import Alamofire
import RxSwift

extension Api {
    struct Oauth {
        private enum Requests: Requestable {
            case accessToken(code: String)
            case login
            
            var host: String {
                return "https://github.com"
            }
            
            var path: String {
                switch self {
                case .accessToken:
                    return "/login/oauth/access_token"
                case .login:
                    return "/login/oauth/authorize?client_id=\(ApiInfomation.get(key: .clientId)!)&redirect_uri=\(ApiInfomation.get(key: .redirectUri)!)&scope=\(ApiInfomation.get(key: .scope)!)"
                }
            }
            
            var method: ApiMethod {
                return .post
            }
            
            var parameter: Parameters {
                switch self {
                case .accessToken(let code):
                    return [
                        "client_id": ApiInfomation.get(key: .clientId)!,
                        "client_secret": ApiInfomation.get(key: .clientSecret)!,
                        "code": code
                    ]
                    
                case .login:
                    return [:]
                }
            }
        }
        
        static var webViewUrl: URL? {
            return URL(string: Requests.login.host + Requests.login.path)
        }
        
        static func requestToken(code: String) -> Observable<Authentication> {
            return Api.create(configure: Requests.accessToken(code: code)).request()
        }
    }
}

