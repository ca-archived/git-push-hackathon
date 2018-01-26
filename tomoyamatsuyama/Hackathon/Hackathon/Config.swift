//
//  Config.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation

class Config {
    private(set) var configPlist: NSMutableDictionary = NSMutableDictionary()
    
    init(){
        guard let filePath = Bundle.main.path(forResource: "Config", ofType:"plist" ) else { return }
        guard let cinfigfile = NSMutableDictionary(contentsOfFile:filePath) else { return }
        self.configPlist = cinfigfile
    }
    
    func get(key: String) -> String {
        if let value: String = configPlist.value(forKey: key) as! String {
            return value
        }
    }
}

