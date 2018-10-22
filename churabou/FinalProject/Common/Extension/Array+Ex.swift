//
//  Array+Ex.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/19.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation

extension Array where Element: Equatable {
    var uniqe: [Element] {
        return reduce(Array<Element>()) { $0.contains($1) ? $0 : $0+[$1] }
    }
}
