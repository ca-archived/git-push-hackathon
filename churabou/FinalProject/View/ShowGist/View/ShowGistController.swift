//
//  ShowGistController.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import WebKit
import RxSwift
import RxCocoa

final class ShowGistController: BaseController {
    
    var gist: Gist!
    
    private var baseView = ShowGistView()
    private var collectionView: UICollectionView { return baseView.collectionView }
    private var tabView: UICollectionView { return baseView.tabView }
    
    override func loadView() {
        view = baseView
    }

    override func viewWillAppear() {
        navigationController?.isNavigationBarHidden = false
    }
    
    override func initializeView() {
        tabView.dataSource = self
        collectionView.dataSource = self
    }
}

extension ShowGistController: UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return gist.files.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let file =  gist.files[indexPath.row]
        // Top Tab Menu
        if collectionView === tabView {
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "tab", for: indexPath) as! TabItemCell
            cell.update(title: file.filename)
            return cell
        }
        // Container
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! ShowGistCell
//        cell.update(index: indexPath.row)
        cell.configure(file: file, showRow: gist.files.count != 1)
        return cell
    }
}
