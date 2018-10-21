import RxCocoa
import RxSwift
import UIKit

protocol GistRepositoryProtocol {
    func fetchAllGists() -> Observable<GistList>
}

final class GistRepository: GistRepositoryProtocol {
    
    private var localDataStore: GistLocalDataStoreProtocol
    private var remoteDataStore: GistRemoteDataStoreProtocol
    
    init(local: GistLocalDataStoreProtocol, remote: GistRemoteDataStoreProtocol) {
        self.localDataStore = local
        self.remoteDataStore = remote
    }
    
    func fetchAllGists() -> Observable<GistList> {
        return remoteDataStore.fetchAllGists()
    }
}


