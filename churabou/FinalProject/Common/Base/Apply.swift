//
//  Apply.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/10.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation


protocol ApplySwift {}

extension ApplySwift {
    
    func apply(closure: (Self) -> Swift.Void) -> Self {
        closure(self)
        return self
    }
}

extension NSObject: ApplySwift {}
