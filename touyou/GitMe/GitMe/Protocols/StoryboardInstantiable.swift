//
//  StoryboardInstantiable.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/16.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MAKR: - Storyboard Instantiable

protocol StoryboardInstantiable: class {

    static var storyboardName: String { get }
}

extension StoryboardInstantiable where Self: UIViewController {

    static var storyboardName: String {

        return String(describing: self)
    }

    static func instantiate() -> Self {

        let storyboard = UIStoryboard(name: storyboardName, bundle: nil)

        guard let controller = storyboard.instantiateInitialViewController() as? Self else {

            assert(false, "Not Found \(storyboardName).swift.")
        }

        return controller
    }
}
