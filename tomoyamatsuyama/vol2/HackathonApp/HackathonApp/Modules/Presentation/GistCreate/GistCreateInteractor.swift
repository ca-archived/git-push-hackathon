import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistCreateInteractor: GistCreateInteractorProtocol {
    private var useCase: GistCreateUseCaseProtocol
    
    init(useCase: GistCreateUseCaseProtocol) {
        self.useCase = useCase
    }
    
    func post(_ gist: GitstCreateModel) -> Completable {
        print("post\(gist)")
        return .empty()
    }
}
