//
//  ShowGistView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/19.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import WebKit

final class ShowGistView: BaseView {
    
    private (set) lazy var collectionView: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        layout.scrollDirection = .horizontal
        layout.itemSize = CGSize(width: UIScreen.main.bounds.width, height: UIScreen.main.bounds.height - 120)
        let c = UICollectionView(frame: .zero, collectionViewLayout: layout)
        c.translatesAutoresizingMaskIntoConstraints = false
        c.backgroundColor = .clear
        c.register(ShowGistCell.self, forCellWithReuseIdentifier: "cell")
        c.backgroundColor = .orange
        c.clipsToBounds = true
        c.isPagingEnabled = true
        return c
    }()
    
    private (set) lazy var tabView: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        let width = (UIScreen.main.bounds.width) / 2
        layout.minimumLineSpacing = 0
        layout.minimumInteritemSpacing = 0
        layout.sectionInset = UIEdgeInsets.init(top: 0, left: 0, bottom: 0, right: 0)
        
        layout.itemSize = CGSize(width: width-30, height: 30)
        layout.scrollDirection = .horizontal
        let c = UICollectionView(frame: .zero, collectionViewLayout: layout)
        c.translatesAutoresizingMaskIntoConstraints = false
        c.backgroundColor = .clear
        c.register(TabItemCell.self, forCellWithReuseIdentifier: "tab")
        c.backgroundColor = .orange
        c.clipsToBounds = true
        c.isPagingEnabled = true
        return c
    }()
    
    override func initializeView() {
        backgroundColor = .white
        addSubview(tabView)
        addSubview(collectionView)
        collectionView.delegate = self
    }
    
    override func initializeConstraints() {

        tabView.snp.makeConstraints { make in
            make.top.left.right.equalToSuperview()
            make.height.equalTo(30)
        }
        
        collectionView.snp.makeConstraints { make in
            make.bottom.left.right.equalToSuperview()
            make.top.equalTo(tabView.snp.bottom)
        }
    }
}

extension ShowGistView: UICollectionViewDelegateFlowLayout {
    
    private var margin: CGFloat { return 0 }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: UIScreen.main.bounds.width,
                      height: collectionView.bounds.height)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets(top: 0, left: margin, bottom: 0, right: margin)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return margin
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return margin
    }
}

extension ShowGistView: UIScrollViewDelegate {
    
    func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
        let index = Int(scrollView.contentOffset.x / scrollView.bounds.width)
        let indexPath = IndexPath(row: index, section: 0)
        
        tabView.visibleCells
            .compactMap { $0 as? TabItemCell }
            .forEach { $0.hilight(indexPath.row) }
        tabView.scrollToItem(at: indexPath, at: .centeredHorizontally, animated: true)
    }
}
