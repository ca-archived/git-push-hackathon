import Alamofire
import RxCocoa
import RxSwift
import UIKit
import WebKit

final class LoginViewController: UIViewController, LoginViewProtocol, StoryboardInstantiatable {
    private var presenter: LoginPresenterProtocol!
    
    private let disposeBag = DisposeBag()
    
    var redirectTrigger: Signal<URLRequest> {
        return redirectRelay.asSignal()
    }
    private let redirectRelay = PublishRelay<URLRequest>()
    
    var transition: Signal<LoginRouter.Route> {
        return transitionRelay.asSignal()
    }
    private let transitionRelay = PublishRelay<LoginRouter.Route>()

    @IBOutlet private weak var webView: WKWebView!
    
    func inject(with presenter: LoginPresenterProtocol) {
        self.presenter = presenter
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        
        loadWebView()
        
        presenter.status
            .subscribeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak self] status in
                guard let `self` = self else { return }

                switch status {
                case .loading:
                    print("loading")
                case .completed:
                    self.transitionRelay.accept(.gistList)
                case .error(let error):
                    print(error)
                }
            })
            .disposed(by: disposeBag)
    }
    
    private func loadWebView() {
        webView.navigationDelegate = self
        webView.uiDelegate = self
        let url = URL(string: OAuth.Authorize.webViewURL.value)!
        let request = URLRequest(url: url)
        webView.load(request)
    }
}

extension LoginViewController: WKNavigationDelegate, WKUIDelegate {
    func webView(_ webView: WKWebView, decidePolicyFor navigationAction: WKNavigationAction, decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {
        
        if let url = navigationAction.request.url {
            print(url.absoluteString)
            if url.absoluteString.contains("hackathon://?code=") {
                redirectRelay.accept(navigationAction.request)
            }
        }
        
        decisionHandler(.allow)
    }
}
