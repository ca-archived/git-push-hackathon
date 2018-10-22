//
//  CreateGistView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

final class CreateGistView: BaseView {

    private (set) var addButton = UIButton().apply { it in
        it.setTitleColor(.white, for: .normal)
        it.setTitle("+", for: .normal)
        it.titleLabel?.font = .boldSystemFont(ofSize: 30)
        it.backgroundColor = .orange
        it.layer.cornerRadius = 30
        it.layer.shadowColor = UIColor.black.cgColor
        it.layer.shadowOffset = .init(width: 2, height: 2)
        it.layer.shadowRadius = 2
        it.layer.shadowOpacity = 0.3
    }

    private var descreptionLabel = UILabel().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.textColor = .gray
        it.font = .systemFont(ofSize: 14)
        it.textAlignment = .left
        it.text = "descreption"
    }
    
    private (set) var descreptionField = UITextField().apply { it in
        it.textColor = .black
        it.borderStyle = .roundedRect
    }
    
    private (set) var tableView = UITableView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.allowsSelection = false
        it.register(CreateGistCell.self, forCellReuseIdentifier: "cell")
    }

//    private (set) var doneButton = UIButton().apply { it in
//        it.setTitleColor(.white, for: .normal)
//        it.setTitle("投稿", for: .normal)
//        it.titleLabel?.font = .boldSystemFont(ofSize: 30)
//        it.backgroundColor = .cyan
//        it.layer.cornerRadius = 6
//        it.layer.shadowColor = UIColor.black.cgColor
//        it.layer.shadowOffset = .init(width: 2, height: 2)
//        it.layer.shadowRadius = 2
//        it.layer.shadowOpacity = 0.3
//    }

    private lazy var tapGesture: UITapGestureRecognizer = {
        return UITapGestureRecognizer(target: self, action: #selector(actionTap))
    }()
    
    @objc private func actionTap() {
        endEditing(true)
    }
    
    override func initializeView() {
        backgroundColor = .white
        addSubview(descreptionLabel)
        addSubview(descreptionField)
        addSubview(tableView)
//        addSubview(doneButton)
        addSubview(addButton)
        tableView.addGestureRecognizer(tapGesture)
    }
    
    override func initializeConstraints() {
        
        descreptionLabel.snp.makeConstraints { make in
            make.top.equalTo(40)
            make.left.equalTo(20)
            make.width.equalTo(80)
            make.height.equalTo(30)
        }
        
        descreptionField.snp.makeConstraints { make in
            make.top.equalTo(40)
            make.left.equalTo(descreptionLabel.snp.right).offset(10)
            make.right.equalTo(-20)
            make.height.equalTo(30)
        }
        
        tableView.snp.makeConstraints { make in
            make.left.right.equalToSuperview()
            make.bottom.equalTo(-65)
            make.top.equalTo(descreptionLabel.snp.bottom).offset(10)
        }
        
//        doneButton.snp.makeConstraints { make in
//            make.left.equalTo(30)
//            make.right.equalTo(addButton.snp.left).offset(-15)
//            make.height.equalTo(50)
//            make.bottom.equalTo(-15)
//        }
//
        addButton.snp.makeConstraints { make in
            make.size.equalTo(60)
            make.right.equalTo(-15)
            make.bottom.equalTo(-15)
        }
    }
}
