//
//  TopView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/09.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import SnapKit

final class TopView: BaseView {
    
    private let buttonH: CGFloat = 50
    
    private lazy var label: UILabel = {
        let l = UILabel()
        l.textColor = .white
        l.font = .systemFont(ofSize: 20)
        l.textAlignment = .center
        l.numberOfLines = 3
        l.text = "git-push-hackthon\n github gist client \n created by churabou"
        return l
    }()

    private (set) lazy var loginButton: UIButton = {
        let b = UIButton()
        b.translatesAutoresizingMaskIntoConstraints = false
        b.setTitle("ログイン", for: .normal)
        b.setTitleColor(.white, for: .normal)
        b.layer.borderColor = UIColor.white.cgColor
        b.layer.borderWidth = 2
        b.layer.cornerRadius = buttonH / 2
        return b
    }()
    
    override func initializeView() {
        backgroundColor = .orange
        addSubview(loginButton)
        addSubview(label)
    }
    
    override func initializeConstraints() {
        
        label.snp.makeConstraints { (make) in
            make.left.right.centerY.equalToSuperview()
            make.height.equalTo(200)
        }
        
        loginButton.snp.makeConstraints { (make) in
            make.height.equalTo(buttonH)
            make.bottom.equalTo(-50)
            make.left.equalTo(50)
            make.right.equalTo(-50)
        }
    }
}
