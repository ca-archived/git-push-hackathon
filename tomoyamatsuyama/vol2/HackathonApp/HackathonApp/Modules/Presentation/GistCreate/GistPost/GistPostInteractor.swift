import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistPostInteractor: GistPostInteractorProtocol {
    
    private var useCase: GistPostUseCaseProtocol
    
    init(useCase: GistPostUseCaseProtocol) {
        self.useCase = useCase
    }
    
    func post(_ gist: GistCreateModel) -> Observable<GistList> {
        return useCase.post(gist)
    }
}

