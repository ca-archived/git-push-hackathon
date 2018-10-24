import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistListInteractor: GistListInteractorProtocol {
    
    private var useCase: GistListUseCaseProtocol
    
    init(useCase: GistListUseCaseProtocol) {
        self.useCase = useCase
    }
    
    func fetchAllGists() -> Observable<GistList> {
        return useCase.fetchAllGists()
    }
}
