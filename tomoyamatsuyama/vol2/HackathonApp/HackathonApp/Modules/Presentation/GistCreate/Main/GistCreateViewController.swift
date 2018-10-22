import Alamofire
import RxCocoa
import RxSwift
import UIKit

class GistCreateViewController: UIViewController, GistCreateViewProtocol, StoryboardInstantiatable {
    
    @IBOutlet private weak var tableView: UITableView! {
        didSet {
            tableView.register(UINib(nibName: String(describing: DescriptionCell.self), bundle: nil), forCellReuseIdentifier: String(describing: DescriptionCell.self))

            tableView.register(UINib(nibName: String(describing: ScopeCell.self), bundle: nil), forCellReuseIdentifier: String(describing: ScopeCell.self))

            tableView.register(UINib(nibName: String(describing: AddNewFile.self), bundle: nil), forCellReuseIdentifier: String(describing: AddNewFile.self))
            tableView.register(UITableViewCell.self, forCellReuseIdentifier: String(describing: UITableViewCell.self))
            tableView.delegate = self
            tableView.dataSource = self
            tableView.tableFooterView = UIView()
        }
    }

    @IBOutlet private weak var dismissButton: UIBarButtonItem!
    @IBOutlet private weak var postButton: UIBarButtonItem!

    private let disposeBag = DisposeBag()
    private var presenter: GistCreatePresenterProtocol!
    
    var refreshTrigger: Signal<Void> {
        return refreshRelay.asSignal()
    }
    private let refreshRelay = PublishRelay<Void>()
    
    var createTrigger: Signal<GitstCreateModel> {
        return createRelay.asSignal()
    }
    private let createRelay = PublishRelay<GitstCreateModel>()
    
    var presentTrigger: Signal<GistCreateRouter.Route> {
        return presentRelay.asSignal()
    }
    private let presentRelay = PublishRelay<GistCreateRouter.Route>()
    
    func inject(_ presenter: GistCreatePresenterProtocol) {
        self.presenter = presenter
    }
    
    private var viewModel = GistCreateViewModel()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        dismissButton.rx.tap
            .asObservable()
            .flatMap { _ -> Observable<GistCreateRouter.Route> in
                return .just(.dismiss)
            }
            .bind(to: presentRelay)
            .disposed(by: disposeBag)
        
        postButton.rx.tap
            .asObservable()
            .flatMap { [weak self] _ -> Observable<GitstCreateModel> in
                guard let `self` = self else { return .empty() }
                return .empty()
            }
            .bind(to: createRelay)
            .disposed(by: disposeBag)
        
        rx.sentMessage(#selector(viewWillAppear(_:)))
            .take(1)
            .map { _ in }
            .bind(to: refreshRelay)
            .disposed(by: disposeBag)
    
        presenter.viewModel
            .subscribeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak self] vm in
                guard let `self` = self else { return }
                self.viewModel = vm
                self.tableView.reloadData()
            })
            .disposed(by: disposeBag)
    }
}


/// 時間があればリファクタ
// RxDataSourses使いたい。
// presenter.viewModel
//            .subscribeOn(MainScheduler.instance)
//            .map { $0.sections }
//            .bind(to: tableView.rx.items) { (tableView, row, section) in
//                switch section.items[row] {
//                case .description:
//                    let cell: DescriptionCell = tableView.dequeueReusableCell()
//
//                    return cell
//
//                case .scope:
//                    let cell: ScopeCell = tableView.dequeueReusableCell()
//                    return cell
//
//                case .file:
//                    let cell: AddNewFile = tableView.dequeueReusableCell()
//                    return cell
//                }
//            }
//            .disposed(by: disposeBag)

extension GistCreateViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let cell = tableView.cellForRow(at: indexPath)
        
    }
}

extension GistCreateViewController: UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return 2
        } else {
            return 1
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        switch viewModel.sections[indexPath.section].items[indexPath.row] {
        case .description:
            let cell: DescriptionCell = tableView.dequeueReusableCell()

            return cell

        case .scope:
            let cell: ScopeCell = tableView.dequeueReusableCell()
            return cell

        case .file:
            let cell: AddNewFile = tableView.dequeueReusableCell()
            return cell
        }
    }
}
