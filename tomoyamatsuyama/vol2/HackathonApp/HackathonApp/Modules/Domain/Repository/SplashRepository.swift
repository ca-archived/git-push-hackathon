import RxCocoa
import RxSwift
import UIKit

protocol SplashRepositoryProtocol {
    func fetchAccessToken() -> Observable<String?>
}

final class SplashRepository: SplashRepositoryProtocol {
    private var localDataStore: SplashLocalDataStoreProtocol
    private var remoteDataStore: SplashRemoteDataStoreProtocol
    
    init(local: SplashLocalDataStoreProtocol, remote: SplashRemoteDataStoreProtocol) {
        self.localDataStore = local
        self.remoteDataStore = remote
    }
    
    func fetchAccessToken() -> Observable<String?> {
        return localDataStore.fetchAccessToken()
    }
}
