//
//  Config.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/22.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation

struct Config {
    
    static var token: String {
        return accessToken?.token ?? ""
    }
    
    static var isLogin: Bool {
        return accessToken != nil
    }
    
    static var accessToken: AccesToken? {
        return UserDefaults.standard.getToken()
    }
}
