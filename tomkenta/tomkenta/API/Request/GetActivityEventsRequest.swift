//
//  GetActivityEventsRequest.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import APIKit

struct GetActivityEventsRequest: ObjectsRequest {
    
    typealias Response = [Activity]
    typealias ResponseElement = Activity
    
    private let page: Int
    
    init(page: Int) {
        self.page = page
    }
    
    var method: HTTPMethod {
        return .get
    }
    
    var path: String {
        return "events"
    }
    
    var queryParameters: [String : Any]? {
        return ["page": page]
    }
}
