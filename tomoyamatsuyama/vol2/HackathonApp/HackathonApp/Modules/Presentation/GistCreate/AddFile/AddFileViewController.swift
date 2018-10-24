import UIKit
import RxCocoa
import RxSwift

final class AddFileViewController: UIViewController, StoryboardInstantiatable {
    @IBOutlet private weak var nextViewButton: UIBarButtonItem!
    @IBOutlet private weak var titleTextField: UITextField!
    @IBOutlet private weak var contentBaseView: UIView! {
        didSet {
            contentBaseView.layer.cornerRadius = Const.cornerRadius
            contentBaseView.layer.borderColor = Const.borderColor
            contentBaseView.layer.borderWidth = Const.borderWidth
        }
    }
    @IBOutlet private weak var contentTextView: UITextView!
    
    fileprivate var viewModel: GistCreateModel!
    
    private let disposeBag = DisposeBag()
    
    func inject(_ viewModel: GistCreateModel) {
        self.viewModel = viewModel
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        Observable
            .combineLatest(titleTextField.rx.text.orEmpty.asObservable(),
                           contentTextView.rx.text.orEmpty.asObservable())
            .map { !($0.0.isEmpty || $0.1.isEmpty) }
            .bind(to: nextViewButton.rx.isEnabled)
            .disposed(by: disposeBag)
        
        nextViewButton.rx.tap
            .subscribe(onNext: { [weak self] _ in
                guard let `self` = self else { return }
                self.viewModel.title = self.titleTextField.text
                self.viewModel.content = self.contentTextView.text
                let viewController = GistPostViewBuilder.build(with: self.viewModel)
                self.navigationController?.pushViewController(viewController, animated: true)
            })
            .disposed(by: disposeBag)
    }

}

private extension AddFileViewController {
    enum Const {
        static let cornerRadius: CGFloat = 9
        static let borderColor = UIColor.gray.cgColor
        static let borderWidth: CGFloat = 0.3
    }
}
