//
//  ObjectsRequest.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import SwiftyJSON
import APIKit

protocol ObjectsRequest: BaseRequest {
    associatedtype ResponseElement
}

extension ObjectsRequest where ResponseElement: BaseResponse {
    
    func response(from object: Any, urlResponse: HTTPURLResponse) throws -> Self.Response {
        do {
            let json = SwiftyJSON.JSON(object)
            
            guard let array = json.array else {
                throw ResponseError.unexpectedObject(object)
            }
            
            var responses = [ResponseElement]()
            
            for element in array {
                let j = try ResponseElement.decode(from: element)
                responses.append(j)
            }
            
            guard let res = responses as? Self.Response else {
                assertionFailure("SingleObject typealias is not [Response]")
                throw ResponseError.unexpectedObject(object)
            }
            return res
        } catch {
            throw ResponseError.unexpectedObject(object)
        }
    }
}

