//
//  UICollectionView+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/01/16.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - Reuse Extension

extension UICollectionView {

    // MARK: Cell

    func register<T: UICollectionViewCell>(_: T.Type) where T: Reusable {

        register(T.self, forCellWithReuseIdentifier: T.defaultReuseIdentifier)
    }

    func register<T: UICollectionViewCell>(_: T.Type) where T: Reusable, T: NibLoadable {

        let nib = UINib(nibName: T.nibName, bundle: Bundle(for: T.self))

        register(nib, forCellWithReuseIdentifier: T.defaultReuseIdentifier)
    }

    func dequeueReusableCell<T: UICollectionViewCell>(forIndexPath indexPath: IndexPath) -> T where T: Reusable {

        guard let cell = dequeueReusableCell(withReuseIdentifier: T.defaultReuseIdentifier, for: indexPath) as? T else {

            fatalError("Error occurred: reuse \(T.defaultReuseIdentifier)")
        }

        return cell
    }

    // MARK: Reusable View

    func register<T: UICollectionReusableView>(_: T.Type, forSupplementaryViewOfKind kind: String) where T: Reusable {

        register(T.self, forSupplementaryViewOfKind: kind, withReuseIdentifier: T.defaultReuseIdentifier)
    }

    func register<T: UICollectionReusableView>(_: T.Type, forSupplementaryViewOfKind kind: String) where T: Reusable, T: NibLoadable {

        let nib = UINib(nibName: T.nibName, bundle: Bundle(for: T.self))

        register(nib, forSupplementaryViewOfKind: kind, withReuseIdentifier: T.defaultReuseIdentifier)
    }

    func dequeueReusableSupplementaryView<T: UICollectionReusableView>(ofKind elementKind: String, for indexPath: IndexPath) -> T where T: Reusable {

        guard let view = dequeueReusableSupplementaryView(ofKind: elementKind, withReuseIdentifier: T.defaultReuseIdentifier, for: indexPath) as? T else {

            fatalError("Error occurred: reuse \(T.defaultReuseIdentifier)")
        }

        return view
    }
}

