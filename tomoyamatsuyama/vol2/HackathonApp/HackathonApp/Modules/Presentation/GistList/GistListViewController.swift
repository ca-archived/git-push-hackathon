import Alamofire
import Cartography
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
    
//    private let loadingView: LoadingView = {
//        let view = LoadingView()
//        return view
//    }()
    
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
    
    private(set) var dismissTrigger = PublishRelay<Void>()
    
    func inject(_ presenter: GistListPresenterProtocol) {
        self.presenter = presenter
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
//        setUpLoadingView()
        
//        presenter.isLoading
//            .subscribeOn(MainScheduler.instance)
//            .bind(to: loadingView.rx.isHidden)
//            .disposed(by: disposeBag)
        
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
    
//    private func setUpLoadingView() {
//        view.addSubview(loadingView)
//
//        constrain(view, loadingView) { view, loadingView in
//            view.edges == loadingView.edges
//        }
//    }
}

extension GistListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 75.0
    }
}
