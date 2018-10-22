//
//  Diff.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/21.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Foundation


protocol DiffModel {
    associatedtype Identifier: Hashable
    var id: Identifier { get set }
    func same(with: Self) -> Bool
}


class Detector {
    
    enum Status {
        case delete(Int)
        case insert(Int)
//        case deletes([Int])
//        case inserts([Int])
//        case move
    }
    
    func detectSingle<T: DiffModel>(newValue: [T], oldValue: [T]) -> Status? {
        
        if newValue.count == oldValue.count { return nil }

        if newValue.count > oldValue.count {
            if let index = newValue.index(where: { new in
                !oldValue.contains(where: { $0.same(with: new) })
            }) {
                return .insert(index)
            }
        }
            
        if newValue.count < oldValue.count {
            if let index = oldValue.index(where: { old in
                !newValue.contains(where: { $0.same(with: old) })
            }) {
                return .delete(index)
            }
        }
        return nil
    }
}

