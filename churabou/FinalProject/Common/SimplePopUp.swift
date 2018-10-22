//
//  SimplePopUp.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/17.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import SnapKit

final class SimplePopUp: BaseController {
    
    enum BSStyle {
        case success, failure, warning
        var color: UIColor {
            switch self {
            case .success: return .green
            case .failure: return .red
            case .warning: return .orange
            }
        }
    }
    
    private static var controller: UIViewController? {
        if let rootViewController = UIApplication.shared.keyWindow?.rootViewController {
            var topViewControlelr: UIViewController = rootViewController
            while let presentedViewController = topViewControlelr.presentedViewController {
                topViewControlelr = presentedViewController
            }
            return topViewControlelr
        } else {
            return nil
        }
    }
    static func show(title: String = "通信エラー", style: BSStyle = .failure) {
        let c = SimplePopUp()
        c.label.text = title
        c.closeButton.backgroundColor = style.color
        DispatchQueue.main.async {
            controller?.present(c, animated: false, completion: nil)
        }
    }
    
    override func commonInit() {
        modalPresentationStyle = .overCurrentContext
    }
    
    private var contentView = UIView().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.backgroundColor = .white
        it.alpha = 0
        it.layer.cornerRadius = 8
    }
    
    private lazy var label = UILabel().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.textColor = .darkGray
        it.font = .boldSystemFont(ofSize: 17)
        it.textAlignment = .center
        it.numberOfLines = 0
    }
    
    private lazy var closeButton = UIButton().apply { it in
        it.translatesAutoresizingMaskIntoConstraints = false
        it.setTitle("閉じる", for: .normal)
        it.titleLabel?.font = .boldSystemFont(ofSize: 20)
        it.setTitleColor(.white, for: .normal)
        it.addTarget(self, action: #selector(actionClose), for: .touchUpInside)
        it.layer.cornerRadius = 4
    }
    
    override func viewDidLoad() {
        
        view.backgroundColor = UIColor(white: 0, alpha: 0.3)
        view.addSubview(contentView)
        
        contentView.addSubview(label)
        contentView.addSubview(closeButton)
        
        contentView.snp.makeConstraints { make in
            make.width.equalTo(250)
            make.height.equalTo(180)
            make.center.equalToSuperview()
        }
        
        label.snp.makeConstraints { make in
            make.left.top.equalTo(20)
            make.right.equalTo(-20)
            make.height.equalTo(100)
        }
        
        closeButton.snp.makeConstraints { make in
            make.left.equalTo(30)
            make.right.equalTo(-30)
            make.height.equalTo(40)
            make.bottom.equalTo(-10)
        }
    }
    
    @objc private func actionClose() {
        dismiss(animated: false, completion: nil)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        contentView.transform = CGAffineTransform.identity.scaledBy(x: 0.95, y: 0.95)
        
        let animator = UIViewPropertyAnimator(duration: 0.4, curve: .easeInOut) {
            self.contentView.alpha = 1
            self.contentView.transform = .identity
        }
        
        animator.startAnimation()
    }
}
