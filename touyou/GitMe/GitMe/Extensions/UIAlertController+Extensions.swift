//
//  UIAlertController+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/26.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - Easy Setup Extensions

extension UIAlertController {

    func addAction(title: String, style: UIAlertActionStyle = .default, handler: ((UIAlertAction) -> Void)? = nil) -> Self {

        let action = UIAlertAction(title: title, style: style, handler: handler)
        self.addAction(action)
        return self
    }

    func show() {

        UIApplication.shared.topPresentedViewController?.present(self, animated: true, completion: nil)
    }
}
