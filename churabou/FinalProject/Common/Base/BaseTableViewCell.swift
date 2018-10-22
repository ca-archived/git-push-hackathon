//
//  BaseTableViewCell.swift
//  NumberGame
//
//  Created by ちゅーたつ on 2018/08/17.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

class BaseTableViewCell: UITableViewCell {
    
    fileprivate var constraintsInitialized = false
    
    override open class var requiresConstraintBasedLayout: Bool {
        return true
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override init(style: UITableViewCellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        initializeView()
    }

    override func updateConstraints() {
        if !constraintsInitialized {
            constraintsInitialized = true
            initializeConstraints()
        }
        super.updateConstraints()
    }
    
    func initializeView() { /* don't write code here */ }
    func initializeConstraints() { /* don't write code here */ }
    
    var indexPath: IndexPath {
        if let tableView = superview as? UITableView,
            let path = tableView.indexPath(for: self) {
            return path
        }
        return .init()
    }
}
