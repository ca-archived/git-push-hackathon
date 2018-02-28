//
//  Token.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/26.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation

struct Token {
    
    static private let accessTokenKey = "TokenAccessTokenKey"
    
    static var oauthToken: String? {
        get {
            return UserDefaults.standard.string(forKey: accessTokenKey)
        }
        set(new) {
            UserDefaults.standard.set(new, forKey: accessTokenKey)
            UserDefaults.standard.synchronize()
        }
    }
}
