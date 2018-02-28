//
//  Reusable.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/16.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: Reusable

protocol Reusable: class {

    static var defaultReuseIdentifier: String { get }
}

extension Reusable where Self: UIView {

    static var defaultReuseIdentifier: String {

        return String(describing: self)
    }
}
