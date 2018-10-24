//
//  Gist.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation

struct Gist: Codable {

    var id = ""
    var user: User
    var files: [File]
    var description: String? = ""
    var createdAt: Date
    
    struct File: Codable {
        var filename = ""
        var language: String? = ""
        var rawUrl: String
        
        enum CodingKeys: String, CodingKey {
            case filename
            case language
            case rawUrl = "raw_url"
        }
    }
    
    enum CodingKeys: String, CodingKey {
        case id
        case user = "owner"
        case files
        case description
        case createdAt = "created_at"
    }
    
    init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        id = try values.decode(String.self, forKey: .id)
        user = try values.decode(User.self, forKey: .user)
        description = try? values.decode(String.self, forKey: .description)
        let dics = try values.decode([String: File].self, forKey: .files)
        files = dics.map { $0.value }
        createdAt = try values.decode(Date.self, forKey: .createdAt)
    }
}


extension Gist: Equatable {
    static func == (lhs: Gist, rhs: Gist) -> Bool {
        return lhs.id == rhs.id
    }
}
