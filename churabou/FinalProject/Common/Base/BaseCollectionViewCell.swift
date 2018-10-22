//
//  BaseCollectionViewCell.swift
//  NumberGame
//
//  Created by ちゅーたつ on 2018/08/17.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

class BaseCollectionViewCell: UICollectionViewCell {
    
    fileprivate var constraintsInitialized = false
    
    override open class var requiresConstraintBasedLayout: Bool {
        return true
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        initializeView()
    }
    
    override func updateConstraints() {
        if !constraintsInitialized {
            constraintsInitialized = true
            initializeConstraints()
        }
        modifyConstraints()
        super.updateConstraints()
    }
    
    func initializeView() { /* don't write code here */ }
    func initializeConstraints() { /* don't write code here */ }
    func modifyConstraints() { /* don't write code here */ }
}
