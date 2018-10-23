import Alamofire
import RxCocoa
import RxSwift
import UIKit

class GistPostViewController: UIViewController, GistPostViewProtocol, StoryboardInstantiatable {
    @IBOutlet private weak var descriptionLabel: UILabel!
    @IBOutlet private weak var titleLabel: UILabel!
    @IBOutlet private weak var scopeSwitch: UISwitch!
    @IBOutlet private weak var postButton: UIBarButtonItem!
    
    var createTrigger: Signal<Void> {
        return createRelay.asSignal()
    }
    private let createRelay = PublishRelay<Void>()
    
    var presentTrigger: Signal<GistPostRouter.Route> {
        return presentRelay.asSignal()
    }
    private let presentRelay = PublishRelay<GistPostRouter.Route>()
    
    var updateSwitchTrigger: Signal<Bool> {
        return updateSwitchRelay.asSignal()
    }
    private let updateSwitchRelay = PublishRelay<Bool>()
    
    private let disposeBag = DisposeBag()
    private var presenter: GistPostPresenterProtocol!

    func inject(_ presenter: GistPostPresenterProtocol) {
        self.presenter = presenter
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpView()
        
        scopeSwitch.rx.isOn
            .bind(to: updateSwitchRelay)
            .disposed(by: disposeBag)
        
        postButton.rx.tap.asObservable()
            .map { _ in }
            .bind(to: createRelay)
            .disposed(by: disposeBag)
    }
    
    private func setUpView() {
        if let title = presenter.viewModel.title, let description = presenter.viewModel.description {
            titleLabel.text = title
            descriptionLabel.text = description
        }
    }
}
