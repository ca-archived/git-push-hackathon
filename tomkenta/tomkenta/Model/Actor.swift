//
//  Actor.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import SwiftyJSON

final class Actor :BaseResponse {
    let id: Int
    let login: String
    let gravatarId: String
    let url: String
    let avatarUrl: String
    
    init(json: SwiftyJSON.JSON) throws {
        id = json["id"].intValue
        login = json["login"].stringValue
        gravatarId = json["gravatar_id"].stringValue
        url = json["url"].stringValue
        avatarUrl = json["avatar_url"].stringValue
    }
}
