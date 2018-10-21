import RxCocoa
import RxSwift
import UIKit

protocol GistListUseCaseProtocol {
    func fetchAllGists() -> Observable<GistList>
}

final class GistListUseCase: GistListUseCaseProtocol {
    private var repository: GistRepositoryProtocol
    
    init(repository: GistRepositoryProtocol) {
        self.repository = repository
    }
    
    func fetchAllGists() -> Observable<GistList> {
        return repository.fetchAllGists()
    }
}

