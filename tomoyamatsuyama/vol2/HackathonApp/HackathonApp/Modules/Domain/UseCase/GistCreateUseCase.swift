import RxCocoa
import RxSwift
import UIKit

protocol GistCreateUseCaseProtocol {
    func fetchAllGists() -> Observable<GistList>
}

final class GistCreateUseCase: GistCreateUseCaseProtocol {
    private var repository: GistRepositoryProtocol
    
    init(repository: GistRepositoryProtocol) {
        self.repository = repository
    }
    
    func fetchAllGists() -> Observable<GistList> {
        return repository.fetchAllGists()
    }
}


