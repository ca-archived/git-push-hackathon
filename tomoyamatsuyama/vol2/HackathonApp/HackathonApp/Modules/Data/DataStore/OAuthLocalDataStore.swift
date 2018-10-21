import RxSwift
import RxCocoa
import Foundation
import UIKit

protocol OAuthLocalDataStoreProtocol {
    func hasAccessToken() -> Observable<Bool>
}

final class OAuthLocalDataStore: OAuthLocalDataStoreProtocol {
    func hasAccessToken() -> Observable<Bool> {
        
        if let _ = OAuth.accessToken.value {
            return .just(true)
        } else {
            return .just(false)
        }
    }
}



