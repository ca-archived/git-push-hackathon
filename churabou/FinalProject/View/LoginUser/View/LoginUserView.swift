//
//  LoginUserView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/20.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import SnapKit


final class LoginUserView: BaseView {
    
    private (set) var popUp = LoginUserPopUp()
    
    override func initializeView() {
        backgroundColor = UIColor.black.withAlphaComponent(0.3)
        addSubview(popUp)
    }
    
    override func initializeConstraints() {
        popUp.snp.makeConstraints { (make) in
            make.center.equalToSuperview()
            make.width.equalTo(300)
            make.height.equalTo(400)
        }
    }
}
