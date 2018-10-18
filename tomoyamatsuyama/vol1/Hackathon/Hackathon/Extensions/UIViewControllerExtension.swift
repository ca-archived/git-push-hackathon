//
//  UIViewControllerExtension.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/01/25.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import UIKit
import Kingfisher

extension UIViewController {
    func startIndicator(indicator: UIActivityIndicatorView) {
        indicator.activityIndicatorViewStyle = .whiteLarge
            indicator.center = self.view.center
            indicator.color = UIColor.gray
            indicator.hidesWhenStopped = true
            self.view.addSubview(indicator)
            indicator.startAnimating()
        }
    
    func stopIndicator(indicator: UIActivityIndicatorView){
        indicator.stopAnimating()
    }
    
    func setImage(imageView: UIImageView, urlString: String) {
        let resourse = URL(string: urlString)
        imageView.kf.setImage(with: resourse)
    }
    
    func imageSet(avatarUrl: String) -> UIImage? {
        guard let imageUrl = URL(string: avatarUrl) else { return nil }
        do {
            let imageData = try Data(contentsOf: imageUrl, options: Data.ReadingOptions.mappedIfSafe)
            let image = UIImage(data: imageData)
            return image
        } catch {
            let image = UIImage(named: "defaultIcon")
            return image
        }
    }
}
