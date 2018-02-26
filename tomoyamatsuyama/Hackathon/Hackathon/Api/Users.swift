//
//  Users.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/26.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation
import Alamofire
import RxSwift

extension Api {
    struct Users {
        private enum Requests: Requestable {
            case user
            case events(name: String)
            
            var host: String {
                return "https://api.github.com"
            }
            
            var path: String {
                switch self {
                case .user:
                    return "/user"
                case .events(let name):
                    return "/users/\(name)/received_events"
                }
            }
            
            var method: ApiMethod {
                switch self {
                case .user:
                    return .get
                case .events:
                    return .get
                }
            }
            
            var parameter: Parameters {
                switch self {
                case .user:
                    guard let accessToken = ApiInfomation.get(key: .acccessToken) else {
                        fatalError("cant get accessToken")
                    }
                    return ["access_token" : accessToken]
                case .events:
                    return [:]
                }
            }
        }
        
        static func getUser() -> Observable<User> {
            return Api.create(configure: Requests.user).request()
        }
        
        static func getEvents(name: String) -> Observable<[Events]> {
            return Api.create(configure: Requests.events(name: name)).request()
        }
    }
}
