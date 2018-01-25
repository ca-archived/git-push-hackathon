//
//  XmlParse.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation

class XmlParse: NSObject {
    var currentFeed = Feeds()
    var feedLists: [Feeds] = []
    var isReadTitle = false
    var isReadName = false
    
    func parseXML(_ urlText: String) -> [Feeds]? {
        guard let url = URL(string: urlText) else{ return nil}
        guard let parser = XMLParser(contentsOf: url) else{ return nil}
        parser.delegate = self
            parser.parse()
        return feedLists
        }
    
    enum TypeOfModel {
        case timeline
        case privateFeed
    }
    
    enum Tag: String {
        case entry = "entry"
        case title = "title"
        case name = "name"
        case media = "media:thumbnail"
        case other = "other"
    }
}
