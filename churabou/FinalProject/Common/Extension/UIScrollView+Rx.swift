//
//  UIScrollView+Rx.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/14.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa

extension Reactive where Base: UIScrollView {
    var reachedBottom: Observable<Void> {
        return contentOffset
            .flatMap { [weak base] contentOffset -> Observable<Bool> in
                guard let scrollView = base else {
                    return Observable.empty()
                }
                let visibleHeight = scrollView.frame.height - scrollView.contentInset.top - scrollView.contentInset.bottom
                let y = contentOffset.y + scrollView.contentInset.top
                let threshold = max(0.0, scrollView.contentSize.height - visibleHeight)
                return  Observable.just(y > threshold)
            }
            .distinctUntilChanged()
            .filter { $0 }
            .map { _ in }
    }
}
