import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistListViewController: UIViewController, GistListViewProtocol, StoryboardInstantiatable {
    @IBOutlet private weak var tableView: UITableView! {
        didSet {
            tableView.register(UINib(nibName: String(describing: GistCell.self), bundle: nil), forCellReuseIdentifier: "cell")
            tableView.delegate = self
            tableView.tableFooterView = UIView()
        }
    }
    
    @IBOutlet private weak var createButton: UIBarButtonItem!
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
            .map { $0.gists }
            // TODO: should create extension of nib
            .bind(to: tableView.rx.items(cellIdentifier: "cell", cellType: GistCell.self)) { _, gist, cell in
                cell.configure(gist)
            }
            .disposed(by: disposeBag)
        
        createButton.rx.tap
            .asObservable()
            .flatMap { _ -> Observable<GistListRouter.Route> in
                return .just(.createGist)
            }
            .bind(to: presentRelay)
            .disposed(by: disposeBag)

        rx.sentMessage(#selector(viewWillAppear(_:)))
            .take(1)
            .map { _ in }
            .bind(to: refreshRelay)
            .disposed(by: disposeBag)
    }
}

extension GistListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 75.0
    }
}
