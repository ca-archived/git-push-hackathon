import RxCocoa
import RxSwift
import UIKit

protocol OAuthRepositoryProtocol {
    func hasAccessToken() -> Observable<Bool>
    func fetchAccessToken(with code: String) -> Observable<Authorization>
}

final class OAuthRepository: OAuthRepositoryProtocol {
    
    private var localDataStore: OAuthLocalDataStoreProtocol
    private var remoteDataStore: OAuthRemoteDataStoreProtocol
    
    init(local: OAuthLocalDataStoreProtocol, remote: OAuthRemoteDataStoreProtocol) {
        self.localDataStore = local
        self.remoteDataStore = remote
    }
    
    func hasAccessToken() -> Observable<Bool> {
        return localDataStore.hasAccessToken()
    }
    
    func fetchAccessToken(with code: String) -> Observable<Authorization> {
        return remoteDataStore.fetchAccessToken(with: code)
    }

}

