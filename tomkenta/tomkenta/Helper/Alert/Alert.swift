//
//  Alert.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/26.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import UIKit

fileprivate struct Index {
    static let noButtonExists: Int    = -1
    static let cancelButton: Int      =  0
    static let destructiveButton: Int =  1
    static let firstOtherButton: Int  =  2
}

struct Alert {
    
    typealias CompletionBlock = (_ alert: Alert, _ buttonIndex: Int) -> ()
    typealias PopoverPresentationControllerBlock = (_ popover: UIPopoverPresentationController) -> ()
    
    private var controller: UIAlertController?
    private var hasCancelButton = false
    private var hasDestructiveButton = false
    private var hasOtherButtons = false
    
    var visible: Bool {
        guard let able = controller?.visible else {
            return false
        }
        return able
    }
    
    var cancelButtonIndex: Int {
        if hasCancelButton {
            return Index.cancelButton
        }
        return Index.noButtonExists
    }
    
    var firstOtherButtonIndex: Int {
        if hasOtherButtons {
            return Index.firstOtherButton
        }
        return Index.noButtonExists
    }
    
    var destructiveButtonIndex: Int {
        if hasDestructiveButton {
            return Index.destructiveButton
        }
        return Index.noButtonExists
    }
    
    @discardableResult
    static func showAlert(on viewController: UIViewController,
                          title: String?,
                          message: String?,
                          cancelButtonTitle: String?,
                          destructiveButtonTitle: String?,
                          otherButtonTitles: [String]?,
                          tapBlock: CompletionBlock?) -> Alert {
        
        var alert = Alert()
        alert.hasCancelButton = cancelButtonTitle != nil
        alert.hasDestructiveButton = destructiveButtonTitle != nil
        otherButtonTitles.map { alert.hasOtherButtons = $0.count > 0 }
        
        alert.controller = UIAlertController.showAlert(on: viewController,
                                                       title: title,
                                                       message: message,
                                                       cancelButtonTitle: cancelButtonTitle,
                                                       destructiveButtonTitle: destructiveButtonTitle,
                                                       otherButtonTitles: otherButtonTitles,
                                                       tapBlock: { (controller, action, index) in
                                                        tapBlock?(alert, index) })
        return alert
    }
    
    @discardableResult
    static func showActionSheet(on viewController: UIViewController,
                                title: String?,
                                message: String?,
                                cancelButtonTitle: String?,
                                destructiveButtonTitle: String?,
                                otherButtonTitles: [String]?,
                                popoverPresentationControllerBlock: PopoverPresentationControllerBlock?,
                                tapBlock: CompletionBlock?) -> Alert {
        
        var alert = Alert()
        alert.hasCancelButton = cancelButtonTitle != nil
        alert.hasDestructiveButton = destructiveButtonTitle != nil
        otherButtonTitles.map { alert.hasOtherButtons = $0.count > 0 }
        
        alert.controller = UIAlertController.showActionSheet(on: viewController,
                                                             title: title,
                                                             message: message,
                                                             cancelButtonTitle: cancelButtonTitle,
                                                             destructiveButtonTitle: destructiveButtonTitle,
                                                             otherButtonTitles: otherButtonTitles,
                                                             popoverPresentationControllerBlock: { (popover) in
        }, tapBlock: { (controller, action, index) in
            tapBlock?(alert, index)
        })
        
        return alert
    }
    
    func dismissAlert(animated: Bool) {
        controller?.dismiss(animated: animated, completion: nil)
    }
}
