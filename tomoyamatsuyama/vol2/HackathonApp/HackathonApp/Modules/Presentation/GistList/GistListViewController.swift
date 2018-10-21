import Alamofire
import RxCocoa
import RxSwift
import UIKit

class GistListViewController: UIViewController, GistListViewProtocol, StoryboardInstantiatable {
    private var presenter: GistListPresenterProtocol!
    
    private let disposeBag = DisposeBag()

    var refreshTrigger: Signal<Void> {
        return refreshRelay.asSignal()
    }
    private let refreshRelay = PublishRelay<Void>()

    var present: Signal<GistListRouter.Route> {
        return presentRelay.asSignal()
    }
    private let presentRelay = PublishRelay<GistListRouter.Route>()
    
    func inject(_ presenter: GistListPresenterProtocol) {
        self.presenter = presenter
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        presenter.viewModel
            .subscribeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak self] viewModel in
                guard let `self` = self else { return }
                print(viewModel)
            })
            .disposed(by: disposeBag)

        rx.sentMessage(#selector(viewWillAppear(_:)))
            .take(1)
            .map { _ in }
            .bind(to: refreshRelay)
            .disposed(by: disposeBag)
    }
}
