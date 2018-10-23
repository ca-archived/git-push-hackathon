import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistPostInteractor: GistPostInteractorProtocol {
    
    private var useCase: GistPostUseCaseProtocol
    
    init(useCase: GistPostUseCaseProtocol) {
        self.useCase = useCase
    }
    
    func post(_ gist: GistCreateModel) -> Completable {
        return useCase.post(gist)
    }
}

