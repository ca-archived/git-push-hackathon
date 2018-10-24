import RxCocoa
import RxSwift
import UIKit

protocol GistPostUseCaseProtocol {
    func post(_ gist: GistCreateModel) -> Observable<GistList>
}

final class GistPostUseCase: GistPostUseCaseProtocol {
    private var repository: GistRepositoryProtocol
    
    init(repository: GistRepositoryProtocol) {
        self.repository = repository
    }
    
    func post(_ gist: GistCreateModel) -> Observable<GistList> {
        return repository.post(gist)
    }
}
