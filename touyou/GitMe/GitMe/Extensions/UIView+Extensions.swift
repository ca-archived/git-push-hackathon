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

    @IBInspectable var shadowOffset: CGSize {
        get {

            return layer.shadowOffset
        }
        set {

            layer.shadowOffset = newValue
        }
    }

    @IBInspectable var shadowRadius: CGFloat {
        get {

            return layer.shadowRadius
        }
        set {

            layer.shadowRadius = newValue
        }
    }

    @IBInspectable var shadowOpacity: Float {
        get {

            return layer.shadowOpacity
        }
        set {

            layer.shadowOpacity = newValue
        }
    }
}
