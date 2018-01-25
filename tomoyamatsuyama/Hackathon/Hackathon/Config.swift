//
//  Config.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation

struct Config {

    enum Config: String {
        case client_id = "b5107ed03329a1ff1b6f"
        case client_secret = "c7c0e8951efaf9df57d4127f11f8e039b56f5c93"
        case redirect_uri = "hackathon://"
        case scope = "user:follow,repo,notifications,gist,read:org"
    }
}
