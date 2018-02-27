//
//  NSMutableAttributedString+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/21.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - Making Attributed String Extensions

extension NSMutableAttributedString {

    enum GitMeStyle {

        case normal
        case bold
    }

    private var boldStyle: [NSAttributedStringKey: Any] {

        return [.font: UIFont(name: "URWDIN-Demi", size: 14.0) ?? UIFont.boldSystemFont(ofSize: 14.0)]
    }

    func append(_ styledString: [(String, GitMeStyle)]) {

        for (str, style) in styledString {

            switch style {
            case .normal:

                self.append(NSAttributedString(string: str))
            case .bold:

                self.append(NSAttributedString(string: str, attributes: boldStyle))
            }
        }
    }
}
