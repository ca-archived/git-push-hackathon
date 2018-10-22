//
//  BaseController.swift
//  NumberGame
//
//  Created by ちゅーたつ on 2018/08/11.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

class BaseController: UIViewController {
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        commonInit()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initializeView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        viewWillAppear()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        viewDidAppear()
    }
    
    func commonInit() {}
    func initializeView() {}
    func viewDidAppear() {}
    func viewWillAppear() {}
    
}
