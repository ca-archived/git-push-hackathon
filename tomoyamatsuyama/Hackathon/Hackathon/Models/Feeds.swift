//
//  Feeds.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation

struct Feeds {
    var avatarUrlString = ""
    var name = ""
    var title = ""
    
    mutating func initialize(){
        self.avatarUrlString = ""
        self.name = ""
        self.title = ""
    }
}
