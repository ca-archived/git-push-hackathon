//
//  BaseView.swift
//  NumberGame
//
//  Created by ちゅーたつ on 2018/08/11.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

class BaseView: UIView {
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        initializeView()
        initializeConstraints()
    }
    
    override func updateConstraints() {
        super.updateConstraints()
    }
    
    func initializeView() { /* don't write code here */ }
    func initializeConstraints() { /* don't write code here */ }
}

