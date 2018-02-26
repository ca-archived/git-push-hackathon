//
//  Activity.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import SwiftyJSON

final class Activity: BaseResponse {
    let id: Int
    let type: String
    let isPublic: Bool
    let createdAt: Date
    let actor: Actor
    let org: Actor
    let repo: Repo
    
    init(json: SwiftyJSON.JSON) throws {
        id = json["id"].intValue
        type = json["type"].stringValue
        isPublic = json["public"].boolValue
        createdAt = Date()
        actor = try Actor(json: json["actor"])
        org = try Actor(json: json["org"])
        repo = try Repo(json: json["repo"])
    }
}
