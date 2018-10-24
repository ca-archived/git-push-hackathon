import Alamofire
import RxCocoa
import RxSwift
import UIKit

final class GistCreateViewController: UIViewController, GistCreateViewProtocol, StoryboardInstantiatable {

    @IBOutlet private weak var dismissButton: UIBarButtonItem!
    @IBOutlet private weak var nextViewButton: UIBarButtonItem!
    @IBOutlet private weak var descriptionTextView: UITextView!
    @IBOutlet private weak var borderView: UIView! {
        didSet {
            borderView.layer.cornerRadius = Const.cornerRadius
            borderView.layer.borderColor = Const.borderColor
            borderView.layer.borderWidth = Const.borderWidth
        }
    }
    
    private let disposeBag = DisposeBag()
    private var presenter: GistCreatePresenterProtocol!
    
    var presentTrigger: Signal<GistCreateRouter.Route> {
        return presentRelay.asSignal()
    }
    private let presentRelay = PublishRelay<GistCreateRouter.Route>()
    
    func inject(_ presenter: GistCreatePresenterProtocol) {
        self.presenter = presenter
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        dismissButton.rx.tap
            .asObservable()
            .flatMap { _ -> Observable<GistCreateRouter.Route> in
                return .just(.dismiss)
            }
            .bind(to: presentRelay)
            .disposed(by: disposeBag)
        
        descriptionTextView.rx.text.orEmpty
            .asObservable()
            .map { !$0.isEmpty }
            .bind(to: nextViewButton.rx.isEnabled)
            .disposed(by: disposeBag)
        
        nextViewButton.rx.tap
            .asObservable()
            .flatMap { [weak self] _ -> Observable<GistCreateRouter.Route> in
                guard let `self` = self else { return .empty() }
                
                let description = self.descriptionTextView.text!
                let viewModel = GistCreateModel(title: nil, content: nil, description: description, isPublic: nil)
                return .just(.addFile(viewModel))
            }
            .bind(to: presentRelay)
            .disposed(by: disposeBag)
        
        rx.sentMessage(#selector(viewWillAppear(_:)))
            .map { _ in }
            .subscribe(onNext: { [weak descriptionTextView] _ in
                guard let descriptionTextView = descriptionTextView else { return }
                descriptionTextView.becomeFirstResponder()
            })
            .disposed(by: disposeBag)
    }
}

private extension GistCreateViewController {
    enum Const {
        static let cornerRadius: CGFloat = 9
        static let borderColor = UIColor.gray.cgColor
        static let borderWidth: CGFloat = 0.3
    }
}
