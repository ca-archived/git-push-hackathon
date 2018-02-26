//
//  UIAlertController+extension.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/26.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import UIKit

fileprivate struct ButtonIndex {
    static let cancel: Int      = 0
    static let destructive: Int = 1
    static let firstOther: Int  = 2
}

extension UIAlertController {
    
    typealias PopoverPresentationControllerBlock = (_ popover: UIPopoverPresentationController) -> ()
    typealias CompletionBlock = (_ controller: UIAlertController, _ action: UIAlertAction, _ buttonIndex: Int) -> ()
    
    // MARK: Properties
    
    @nonobjc var visible: Bool {
        return view.superview != nil
    }
    
    @nonobjc var cancelButtonIndex: Int {
        return ButtonIndex.cancel
    }
    
    @nonobjc var firstOtherButtonIndex: Int {
        return ButtonIndex.firstOther
    }
    
    @nonobjc var destructiveButtonIndex: Int {
        return ButtonIndex.destructive
    }
    
    // MARK: Methods
    
    class func showAlert(on viewController: UIViewController,
                         title: String?,
                         message: String?,
                         cancelButtonTitle: String?,
                         destructiveButtonTitle: String?,
                         otherButtonTitles: [String]?,
                         tapBlock: CompletionBlock?) -> UIAlertController {
        
        return show(on: viewController,
                    title: title,
                    message: message,
                    preferredStyle: .alert,
                    cancelButtonTitle: cancelButtonTitle,
                    destructiveButtonTitle: destructiveButtonTitle,
                    otherButtonTitles: otherButtonTitles,
                    popoverPresentationControllerBlock:nil,
                    tapBlock: tapBlock)
    }
    
    class func showActionSheet(on viewController: UIViewController,
                               title: String?,
                               message: String?,
                               cancelButtonTitle: String?,
                               destructiveButtonTitle: String?,
                               otherButtonTitles: [String]?,
                               popoverPresentationControllerBlock: PopoverPresentationControllerBlock?,
                               tapBlock: CompletionBlock?) -> UIAlertController {
        
        return show(on: viewController,
                    title: title,
                    message: message,
                    preferredStyle: .actionSheet,
                    cancelButtonTitle: cancelButtonTitle,
                    destructiveButtonTitle: destructiveButtonTitle,
                    otherButtonTitles: otherButtonTitles,
                    popoverPresentationControllerBlock: popoverPresentationControllerBlock,
                    tapBlock: tapBlock)
    }
    
    private class func show(on viewController: UIViewController,
                            title: String?,
                            message: String?,
                            preferredStyle: UIAlertControllerStyle,
                            cancelButtonTitle: String?,
                            destructiveButtonTitle: String?,
                            otherButtonTitles: [String]?,
                            popoverPresentationControllerBlock: PopoverPresentationControllerBlock?,
                            tapBlock: CompletionBlock?) -> UIAlertController {
        
        let strongAlertController = UIAlertController(title: title, message: message, preferredStyle: preferredStyle)
        weak var weakAlertController = strongAlertController
        
        if let title = cancelButtonTitle {
            let cancelAction = UIAlertAction(title: title,
                                             style: .cancel,
                                             handler: { (action) in
                                                tapBlock?(weakAlertController ?? strongAlertController, action, ButtonIndex.cancel)
            })
            weakAlertController?.addAction(cancelAction)
        }
        
        if let title = destructiveButtonTitle {
            let destructiveAction = UIAlertAction(title: title,
                                                  style: .destructive,
                                                  handler: { (action) in
                                                    tapBlock?(weakAlertController ?? strongAlertController, action, ButtonIndex.destructive)
            })
            weakAlertController?.addAction(destructiveAction)
        }
        
        if let titles = otherButtonTitles {
            let lastIndex = titles.count - 1
            for i in 0...lastIndex {
                let otherAction = UIAlertAction(title: titles[i],
                                                style: .default,
                                                handler: { (action) in
                                                    tapBlock?(weakAlertController ?? strongAlertController, action, ButtonIndex.firstOther + i)
                })
                weakAlertController?.addAction(otherAction)
            }
        }
        
        if let popover = strongAlertController.popoverPresentationController {
            popoverPresentationControllerBlock?(popover)
        }
        
        viewController.present(weakAlertController ?? strongAlertController, animated: true, completion: nil)
        
        return weakAlertController ?? strongAlertController
    }
}
