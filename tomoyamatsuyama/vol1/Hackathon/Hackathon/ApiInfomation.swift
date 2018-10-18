//
//  ApiInfomation.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/26.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation

class ApiInfomation {
    enum Key {
        case scope
        case redirectUri
        case clientSecret
        case clientId
        case acccessToken
        
        var value: String {
            switch self {
            case .scope:
                return "scope"
            case .redirectUri:
                return "redirect_uri"
            case .clientSecret:
                return "client_secret"
            case .clientId:
                return "client_id"
            case .acccessToken:
                return "access_token"
            }
        }
    }
    
    private static var configPlist: NSMutableDictionary {
        guard let filePath = Bundle.main.path(forResource: "Config", ofType:"plist" ) else {
            fatalError("cant find path of config file")
        }
        guard let cinfigfile = NSMutableDictionary(contentsOfFile:filePath) else {
            fatalError("cant find config file")
        }
        return cinfigfile
    }
    
    static func set(key: Key, value: String) {
        switch key {
        case .acccessToken:
            UserDefaults.standard.set(value, forKey: key.value)
            return
        default:
            return
        }
        
    }
    
    static func get(key: Key) -> Any? {
        switch key {
        case .acccessToken:
            guard let value = UserDefaults.standard.string(forKey: key.value) else {
                return nil
            }
            return value
        default:
            guard let value = configPlist.value(forKey: key.value) else {
                return nil
            }
            return value
        }
    }
    
}

