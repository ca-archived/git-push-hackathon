//
//  BaseRequest.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/25.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import APIKit

protocol BaseRequest: APIKit.Request {}

extension BaseRequest {
    var baseURL: URL {
        return URL(string: "https://api.github.com")!
    }
    
    var headerFields: [String: String] {
        return ["Authorization": "token "]
    }
    
    func intercept(object: Any, urlResponse: HTTPURLResponse) throws -> Any {
        guard(200 ..< 300).contains(urlResponse.statusCode) else {
            throw GitHubAppError(object: object, url: urlResponse.url)
        }
        return object
    }
}

extension BaseRequest where Response: Decodable {
    var dataParser: DataParser {
        return DecodableDataParser()
    }
    
    func response(from object: Any, urlResponse: HTTPURLResponse) throws -> Self.Response {
        guard let data = object as? Data else {
            throw ResponseError.unexpectedObject(object)
        }
        do {
            return try JSONDecoder().decode(Response.self, from: data)
        } catch {
            throw ResponseError.unexpectedObject(object)
        }
    }
}







