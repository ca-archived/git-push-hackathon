//
//  BaseRequest.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/25.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import APIKit
import SwiftyJSON

protocol BaseRequest: APIKit.Request {}

extension BaseRequest {
    var baseURL: URL {
        return URL(string: "https://api.github.com")!
    }
    
    var headerFields: [String: String] {
        let token = Token.oauthToken ?? ""
        return ["Authorization": "token \(token)",
                "Accept": "application/json"
        ]
    }
    
    func intercept(object: Any, urlResponse: HTTPURLResponse) throws -> Any {
        print(object)
        guard(200 ..< 300).contains(urlResponse.statusCode) else {
            throw GitHubAppError(object: object, url: urlResponse.url)
        }
        return object
    }
}

extension BaseRequest where Response: BaseResponse {
    func response(from object: Any, urlResponse: HTTPURLResponse) throws -> Self.Response {
        do {
            let json = SwiftyJSON.JSON(object)
            return try Response.decode(from: json)
        } catch {
            throw ResponseError.unexpectedObject(object)
        }
    }
}
