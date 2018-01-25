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
    }
}
