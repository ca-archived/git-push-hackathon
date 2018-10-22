//
//  AcceccToken.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation

struct AccesToken: Codable {
    var token = ""
    var scope = ""
    var type = ""
    
    enum CodingKeys: String, CodingKey {
        case token = "access_token"
        case scope
        case type = "token_type"
    }
}
