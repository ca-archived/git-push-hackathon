//
//  GithubApiManager.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation
import Alamofire

class GithubApiManager {
    static func getCodeFromCallBackUrl(callBackUrl: URL){
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
        requestToken(code: code, completion: { isStatus, resopnse in
            print(isStatus)
        })
    }
    
    static func requestToken(code: String, completion: ((Bool, String) -> Void)? = nil) {
        let getTokenPath: String = "https://github.com/login/oauth/access_token"
        let parameter = ["client_id": Config.Config.client_id.rawValue, "client_secret": Config.Config.client_secret.rawValue, "code": code]
        Alamofire.request(getTokenPath, method: .post, parameters: parameter).response { response in
            let isStatus = self.checkResponse(statusCode: response.response?.statusCode)
            if let data = response.data, let utf8Text = String(data: data, encoding: .utf8) {
                    completion?(isStatus, utf8Text)
            }
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
}
