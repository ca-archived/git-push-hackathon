//
//  URL+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/30.
//  Copyright © 2018 touyou. All rights reserved.
//

import Foundation

// MARK: - Add Query Extension

extension URL {

    func queryAdded(name: String, value: String?) -> URL? {

        guard var components = URLComponents(url: self, resolvingAgainstBaseURL: nil != self.baseURL) else {

            return nil
        }
        components.queryItems = [URLQueryItem(name: name, value: value)]
        return components.url
    }
}
