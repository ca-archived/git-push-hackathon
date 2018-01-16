//
//  GitHubConstant.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/16.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

class GitHubConstant {

    // ここで取り出せないということはセットアップができていないということなので落としている

    static internal var clientPlist: NSDictionary? {

        guard let filePath = Bundle.main.path(forResource: "ClientKey", ofType: "plist") else {

            fatalError("Please create ClientKey.plist.")
        }
        return NSDictionary(contentsOfFile: filePath)
    }

    static internal var clientKey: String {

        guard let _clientKey = GitHubConstant.clientPlist?["client_key"] as? String else {

            fatalError("Please set client key.")
        }
        return _clientKey
    }

    static internal var clientSecret: String {

        guard let _clientSecret = GitHubConstant.clientPlist?["client_secret"] as? String else {

            fatalError("Please set client secret.")
        }
        return _clientSecret
    }
}
