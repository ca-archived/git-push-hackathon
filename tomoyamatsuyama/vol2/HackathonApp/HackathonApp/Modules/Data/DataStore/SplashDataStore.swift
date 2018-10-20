import RxSwift
import RxCocoa
import Foundation
import UIKit

protocol SplashLocalDataStoreProtocol {
    func fetchAccessToken() -> Observable<String?>
}

protocol SplashRemoteDataStoreProtocol {}

final class SplashLocalDataStore: SplashLocalDataStoreProtocol {
    func fetchAccessToken() -> Observable<String?> {
        
        if let accessToken = OAuth.accessToken.value {
            return .just(accessToken)
        
        } else {
            return .just(nil)
        }
    }
}

final class SplashRemoteDataStore: SplashRemoteDataStoreProtocol {
    
}
