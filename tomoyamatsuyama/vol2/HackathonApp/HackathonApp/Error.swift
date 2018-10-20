import UIKit
import Foundation

struct Error {
    let kind: Kind
    
    enum Kind {
        case lostConnection
        
        var title: String {
            switch self {
            case .lostConnection:
                return "ネットワークに接続されていません"
            }
        }
    }
}
