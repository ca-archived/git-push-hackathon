//
//  TabItemCell.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/19.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

final class TabItemCell: BaseCollectionViewCell {
    
    //    private var bottomBorder = CALayer().apply { it in
    //        it.backgroundColor = UIColor.gray.cgColor
    //    }
    
    private var bottomBorder = UIView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.backgroundColor = .white
    }
    
    private var label = UILabel().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.textAlignment = .center
        it.font = .systemFont(ofSize: 14)
        it.clipsToBounds = true
        it.backgroundColor = .white
    }
    
    override func initializeView() {
        contentView.addSubview(label)
        contentView.addSubview(bottomBorder)
    }
    
    override func initializeConstraints() {
        
        label.snp.makeConstraints({ make in
            make.left.right.top.equalToSuperview()
            make.bottom.equalTo(-2)
        })
        
        bottomBorder.snp.makeConstraints({ make in
            make.left.right.bottom.equalToSuperview()
            make.height.equalTo(2)
        })
    }
    
    func update(title: String) {
        label.text = title
    }
    
    func hilight(_ row: Int) {
        if let collectionView = superview as? UICollectionView,
            let indexPath = collectionView.indexPath(for: self) {
            
            var color: UIColor = indexPath.row == row ? .orange : .black
            label.textColor = color
            
            color = indexPath.row == row ? .black : .white
            bottomBorder.backgroundColor = color
        }
    }
}
