//
//  NibLoadable.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/16.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - Nib Loadable

protocol NibLoadable: class {

    static var nibName: String { get }
}

extension NibLoadable where Self: UIView {

    static var nibName: String {

        return String(describing: self)
    }
}
