//
//  UIViewController+Ex.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/09.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

extension UIViewController {

    func addLeftBarButtonToClose() {
        
        let close = UIBarButtonItem(barButtonSystemItem: .stop,
                                    target: self,
                                    action: #selector(__actionClose))
        navigationItem.leftBarButtonItem = close
    }
    
    @objc private func __actionClose() {
        dismiss(animated: true, completion: nil)
    }
}
