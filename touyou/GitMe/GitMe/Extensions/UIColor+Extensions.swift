//
//  UIColor+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/08.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - Project Color

extension UIColor {

    struct GitMe {

        static let darkGray = UIColor(hex: "333333")
        static let gray = UIColor(hex: "666666")
        static let lightGray = UIColor(hex: "EEEEEE")
    }
}

// MARK: - Initialization Extensions

extension UIColor {

    convenience init(hex: String) {

        let colorString = hex.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines).uppercased().filter { "#" != $0 }
        let alpha: CGFloat = colorString.count == 6 ? 1.0 : 0.0
        var rgb: UInt32 = 0
        Scanner(string: colorString).scanHexInt32(&rgb)

        self.init(red: CGFloat((rgb & 0xFF0000) >> 16) / 255.0, green: CGFloat((rgb & 0x00FF00) >> 8) / 255.0, blue: CGFloat(rgb & 0x0000FF) / 255.0, alpha: alpha)
    }
}

/// String to UIColor Utility
/// ex. colorString.color == UIColor(hex: colorString)
extension String {

    var color: UIColor? {

        return UIColor(hex: self)
    }
}
