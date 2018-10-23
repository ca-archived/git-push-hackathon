import UIKit
import RxCocoa
import RxSwift

class AddFileViewController: UIViewController, StoryboardInstantiatable {
    @IBOutlet private weak var nextViewButton: UIBarButtonItem!
    @IBOutlet private weak var titleTextField: UITextField!
    @IBOutlet private weak var contentBaseView: UIView! {
        didSet {
            contentBaseView.layer.cornerRadius = 12
            contentBaseView.layer.borderColor = UIColor.gray.cgColor
            contentBaseView.layer.borderWidth = 0.3
        }
    }
    @IBOutlet private weak var contentTextView: UITextView!
    
    fileprivate var viewModel: GistCreateModel?
    
    private let disposeBag = DisposeBag()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        Observable
            .combineLatest(titleTextField.rx.text.orEmpty.asObservable(), contentTextView.rx.text.orEmpty.asObservable())
            .map { !($0.0.isEmpty || $0.1.isEmpty) }
            .bind(to: nextViewButton.rx.isEnabled)
            .disposed(by: disposeBag)
        
        nextViewButton.rx.tap
            .subscribe(onNext: { [weak self] _ in
                guard let `self` = self else { return }
                guard var viewModel = self.viewModel else { return }
                viewModel.title = self.titleTextField.text
                viewModel.content = self.contentTextView.text
                let viewController = GistPostViewBuilder.build(with: viewModel)
                self.navigationController?.pushViewController(viewController, animated: true)
            })
            .disposed(by: disposeBag)
    }

}

struct AddFileViewBuilder {
    static func build(with viewModel: GistCreateModel) -> AddFileViewController {
        let viewController: AddFileViewController = .instantiate()
        viewController.viewModel = viewModel
        return viewController
    }
}
