//
//  HUD.swift
//  push-
//
//  Created by ちゅーたつ on 2018/02/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

class HUDView: UIView {
    
    func setUp(_ view: UIView) {
        
        frame = view.frame
        view.addSubview(self)
        initializeView()
        hide()
    }
    
    fileprivate var indicator = UIActivityIndicatorView()
    fileprivate var wrapperView = UIView()
    fileprivate var label = UILabel()
    
    func initializeView() {
        
        backgroundColor = UIColor(white: 0, alpha: 0)
        wrapperView.backgroundColor = UIColor(white: 0.3, alpha: 0.5)
        wrapperView.layer.cornerRadius = 8
        wrapperView.frame.size = CGSize(width: 80, height: 80)
        wrapperView.center = center
        addSubview(wrapperView)
        
        indicator.frame = CGRect(x: 0, y: 0, width: 80, height: 80)
        wrapperView.addSubview(indicator)
        label.frame = CGRect(x: 0, y: 50, width: 80, height: 30)
        label.text = "loading"
        label.textAlignment = .center
        wrapperView.addSubview(label)
    }
    
    func show() {
        alpha = 1
        indicator.startAnimating()
    }
    
    func hide() {
        alpha = 0
        indicator.stopAnimating()
    }
}
