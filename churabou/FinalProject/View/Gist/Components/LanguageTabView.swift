//
//  LanguageTabView.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/19.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import UIKit

//テキストが1行の場合のみ垂直方向に中央揃えできる。
class VCTextLayer : CATextLayer {

    override func draw(in ctx: CGContext) {
        let yDiff = (bounds.size.height-fontSize) / 2 - fontSize / 10
        ctx.saveGState()
        ctx.translateBy(x: 0.0, y: yDiff)
        super.draw(in: ctx)
        ctx.restoreGState()
    }
}

final class LanguageTagView: BaseView {
    
    private var baseView = UIView()
    private var textLayers: [CATextLayer] = []
    private let max = 3
    
    func configure(languages: [String]) {
        
        let margin: CGFloat = 10
        var dx: CGFloat = 0
        
        textLayers.forEach { $0.isHidden = true }
        for i in 0..<min(max, languages.count) {
            let label = textLayers[i]
            label.isHidden = false
            dx += margin
            let labelW = labelSize(text: languages[i]).width + 20
            label.frame = CGRect(x: dx, y: 0, width: labelW, height: 20)
            dx += labelW
            label.string = languages[i]
            // 3個目がはみ出そうならreturn
        }
    }
    
    override func initializeView() {
        backgroundColor = .white
        
        let color: UIColor = .pink
        let height: CGFloat = 20
        
        (0..<max).forEach { index in
            let label = VCTextLayer()
            label.contentsScale = UIScreen.main.scale
            label.font = UIFont.systemFont(ofSize: 12)
            label.fontSize = 12
//            label.speed = 0
            label.contentsScale = UIScreen.main.scale
            label.alignmentMode = kCAAlignmentCenter
            label.foregroundColor = color.cgColor
            label.cornerRadius = height / 2
            label.borderWidth = 1
            label.borderColor = color.cgColor
            layer.addSublayer(label)
            textLayers.append(label)
        }
    }
    
    func labelSize(text: String) -> CGSize {
        return (text as NSString).size(withAttributes: [.font: UIFont.systemFont(ofSize: 12)])
    }
}
