//
//  UserDefaults+Ex.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/20.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation


protocol UserDefaultsService {
    func setToken(_ token: AccesToken)
    func getToken() -> AccesToken?
    func setUser(_ user: User)
    func getUser() -> User?
}

extension UserDefaults {
    
    private var tokenKey: String {
        return "AccesTokenKey"
    }
    
    private var userKey: String {
        return "UserKey"
    }
}

extension UserDefaults: UserDefaultsService {
    
    func getToken() -> AccesToken? {
        if let data = UserDefaults.standard.data(forKey: tokenKey) {
            return try? JSONDecoder().decode(AccesToken.self, from: data)
        } else {
            return nil
        }
    }
    
    func setToken(_ token: AccesToken) {
        if let data = try? JSONEncoder().encode(token) {
            UserDefaults.standard.set(data, forKey: tokenKey)
        }
    }
    
    
    func getUser() -> User? {
        if let data = UserDefaults.standard.data(forKey: tokenKey) {
            return try? JSONDecoder().decode(User.self, from: data)
        } else {
            return nil
        }
    }
    
    func setUser(_ user: User) {
        if let data = try? JSONEncoder().encode(user) {
            UserDefaults.standard.set(data, forKey: tokenKey)
        }
    }
}
