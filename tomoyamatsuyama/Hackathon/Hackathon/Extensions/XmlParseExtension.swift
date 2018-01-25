//
//  XmlParseExtension.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation

extension XmlParse: XMLParserDelegate {
    func parserDidStartDocument(_ parser: XMLParser) {
        print("start")
    }
    
    func parser(_ parser: XMLParser, didStartElement elementName: String, namespaceURI: String?, qualifiedName qName: String?, attributes attributeDict: [String : String]) {
        switch elementName {
            case Tag.entry.rawValue:
                currentFeed.initialize()
            case Tag.title.rawValue:
                isReadTitle = false
            case Tag.media.rawValue:
                currentFeed.avatarUrlString = attributeDict["url"]!
            case Tag.name.rawValue:
                isReadName = false
            default:
                return
        }
    }
    
    func parser(_ parser: XMLParser, foundCharacters string: String) {
        if !isReadTitle {
            currentFeed.title = string
                isReadTitle = true
        } else if !isReadName {
            currentFeed.name = string
            isReadName = true
        }
    }
    
    func parser(_ parser: XMLParser, didEndElement elementName: String, namespaceURI: String?, qualifiedName qName: String?) {
        if elementName == "entry" {
            feedLists.append(currentFeed)
        }
    }
    
    func parserDidEndDocument(_ parser: XMLParser) {
        print("finished")
    }
    
    func parser(_ parser: XMLParser, parseErrorOccurred parseError: Error) {
        print("error:" + parseError.localizedDescription)
        }
    }
