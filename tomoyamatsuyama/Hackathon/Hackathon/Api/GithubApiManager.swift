//
//  GithubApiManager.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation
import Alamofire

class GithubApiManager: NSObject {
    static func getCodeFromCallBackUrl(callBackUrl: URL, completion: ((Bool) -> Void)? = nil) {
        var code = ""
        guard let components = URLComponents(url: callBackUrl, resolvingAgainstBaseURL: false) else { return }
        guard let items = components.queryItems else { return }
        for item in items {
            if item.name == "code" {
                guard let value = item.value else { return }
                code = value
                break
            }
        }
        getOauth(code: code, completion: { isStatus, oauth in
            UserDefaults.standard.set(oauth, forKey: "access_token")
                completion?(isStatus)
        })
    }
    
    static func getOauth(code: String, completion: ((Bool, String) -> Void)? = nil) {
        requestToken(code: code, completion: { responseToken in
            guard let response = responseToken else { return }
            guard let data = response.data else { return }
            guard let dataString = String(data: data, encoding: .utf8) else { return }
            guard let responseOfResponse = response.response else { return }
            let isStatus = self.checkResponse(statusCode: responseOfResponse.statusCode)
            let oauth = self.getAccessTokenFromTokenResponse(response: dataString)
            completion?(isStatus, oauth)
            
        })
    }
    
    static func requestToken(code: String, completion: ((DefaultDataResponse?) -> Void)? = nil) {
        let getTokenPath: String = "https://github.com/login/oauth/access_token"
        let config = Config()
        let parameter = ["client_id": config.get(key: "client_id"), "client_secret": config.get(key: "client_secret"), "code": code]
        Alamofire.request(getTokenPath, method: .post, parameters: parameter).response { response in
            completion?(response)
        }
    }
    
    static func checkResponse(statusCode: Int?) -> Bool {
        guard let statusCode = statusCode else { return false }
        if 200 <= statusCode || statusCode < 300 {
            return true
        } else {
            return false
        }
    }
    
    static func getAccessTokenFromTokenResponse(response: String) -> String {
        let responseItems = response.split(separator: "&")
        var oauth: String = ""
        for responseItem in responseItems {
            let responseItemDic = responseItem.split(separator: "=")
            if String(responseItemDic[0]) == "access_token" {
                oauth = String(responseItemDic[1])
            }
        }
        return oauth
    }
}
