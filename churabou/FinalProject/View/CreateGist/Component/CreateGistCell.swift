//
//  CreateGistCell.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa

final class CreateGistCell: BaseTableViewCell {
    
    var store: CreateGistFileStore?
    
    private let bag = DisposeBag()
    
    func configure(store: CreateGistFileStore, file: File) {
        self.store = store
        fileNameField.text = file.name
        contentTextView.text = file.content
    }
    
    static let height: CGFloat = 300
    
    private (set) lazy var fileNameField = UITextField().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.textColor = .black
        it.borderStyle = .roundedRect
        it.addTarget(self, action: #selector(fileNameFieldChanged), for: .editingChanged)
    }
    
    private (set) lazy var contentTextView = UITextView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.textColor = .black
        it.layer.borderColor = UIColor.gray.cgColor
        it.layer.borderWidth = 2
        it.delegate = self
    }
    
    private var headerLayer = CALayer().apply { it in
        it.backgroundColor = UIColor.lightGray.withAlphaComponent(0.2).cgColor
        it.borderColor = UIColor.gray.cgColor
        it.borderWidth = 1
        it.cornerRadius = 4
        it.frame.origin = CGPoint(x: 10, y: 10)
        it.frame.size = CGSize(width: UIScreen.main.bounds.width-20, height: 40)
    }
    
    override func initializeView() {
        contentView.layer.addSublayer(headerLayer)
        contentView.addSubview(fileNameField)
        contentView.addSubview(contentTextView)
    }
    
    override func initializeConstraints() {
        fileNameField.snp.makeConstraints { make in
            make.top.left.equalTo(15)
            make.width.equalTo(150)
            make.height.equalTo(30)
        }
        
        contentTextView.snp.makeConstraints { make in
            make.top.equalTo(fileNameField.snp.bottom).offset(15)
            make.left.equalTo(20)
            make.right.equalTo(-20)
            make.bottom.equalTo(-10)
        }
    }
    
    @objc private func fileNameFieldChanged() {
        store?.dispatch(action: .updateFileName(row: indexPath.row, fileNameField.text ?? ""))
    }
}

extension CreateGistCell: UITextViewDelegate {
    
    func textViewDidChange(_ textView: UITextView) {
        store?.dispatch(action: .updateFileContent(row: indexPath.row, contentTextView.text ?? ""))
    }
}
