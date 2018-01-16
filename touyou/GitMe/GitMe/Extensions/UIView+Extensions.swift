//
//  UIView+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/16.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - Layout Extensions

extension UIView {

    @IBInspectable var cornerRadius: CGFloat {
        get {

            return layer.cornerRadius
        }
        set {

            layer.cornerRadius = newValue
            layer.masksToBounds = newValue > 0
        }
    }

    @IBInspectable var borderWidth: CGFloat {
        get {

            return layer.borderWidth
        }
        set {

            layer.borderWidth = newValue
        }
    }

    @IBInspectable var borderColor: UIColor? {
        get {

            return UIColor(cgColor: layer.borderColor!)
        }
        set {

            layer.borderColor = newValue?.cgColor
        }
    }
}
