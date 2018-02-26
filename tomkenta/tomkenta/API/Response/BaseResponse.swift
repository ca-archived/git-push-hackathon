//
//  BaseResponse.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import SwiftyJSON

protocol BaseResponse {
    init (json: SwiftyJSON.JSON) throws
}

extension BaseResponse {
    static func decode(from json: SwiftyJSON.JSON) throws -> Self {
        return try Self(json: json)
    }
}

