//
//  Repo.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import SwiftyJSON

final class Repo: BaseResponse {
    let id: Int
    let name: String
    let url: String
    
    init(json: SwiftyJSON.JSON) throws {
        id = json["id"].intValue
        name = json["name"].stringValue
        url = json["url"].stringValue
    }
}
