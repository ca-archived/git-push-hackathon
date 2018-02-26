//
//  Instantiatable.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

protocol Instantiatable: class {
    static var storyboardName: String { get }
}

extension Instantiatable where Self: UIViewController {
    static func instantiate() -> Self {
        let storyboard = UIStoryboard(name: storyboardName, bundle: nil)
        guard let vc = storyboard.instantiateInitialViewController() as? Self else { fatalError("error") }
        return vc
    }
}
