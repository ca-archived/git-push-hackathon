//
//  UIViewControllerExtension.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit

extension UIViewController {
    func showIndicator(indicator: UIActivityIndicatorView) {
        indicator.activityIndicatorViewStyle = .whiteLarge
            indicator.center = self.view.center
            indicator.color = UIColor.gray
            indicator.hidesWhenStopped = true
            self.view.addSubview(indicator)
            self.view.bringSubview(toFront: indicator)
            indicator.startAnimating()
        }
    
    func stopIndecator(indicator: UIActivityIndicatorView){
        indicator.stopAnimating()
    }
}
