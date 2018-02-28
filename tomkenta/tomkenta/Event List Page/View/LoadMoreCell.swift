//
//  LoadMoreCell.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/27.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import UIKit

final class LoadMoreCell: UITableViewCell {
    
    private lazy var indicator: UIActivityIndicatorView = {
        let indicator = UIActivityIndicatorView(activityIndicatorStyle: .gray)
        indicator.color = .gray
        return indicator
    }()
    
    override init(style: UITableViewCellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        isUserInteractionEnabled = false
        backgroundColor = UIColor.clear
        selectionStyle = .none
        contentView.addSubview(indicator)
        indicator.startAnimating()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        indicator.center = contentView.center
    }
    
    func startAnimating() {
        indicator.startAnimating()
    }
    
    func stopAnimating() {
        indicator.stopAnimating()
    }

    class func height() -> CGFloat {
        return 40.0
    }
}
