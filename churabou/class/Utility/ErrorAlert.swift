//
//  ErrorAlert.swift
//  push-
//
//  Created by ちゅーたつ on 2018/02/26.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

class ErrorAlert {
    
    class func show(_ controller: UIViewController) {
        
        let alert = UIAlertController(title: "通信エラー", message: "", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "閉じる", style: .default, handler: nil))
        controller.present(alert, animated: true, completion: nil)
    }
}
