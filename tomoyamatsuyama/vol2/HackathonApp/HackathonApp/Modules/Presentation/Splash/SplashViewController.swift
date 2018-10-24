import RxCocoa
import RxSwift
import UIKit
import Cartography

final class SplashViewController: UIViewController, SplashViewProtocol, StoryboardInstantiatable {
    
    private let loadingView: LoadingView = {
        let view = LoadingView()
        return view
    }()
    
    private var presenter: SplashPresenterProtocol!
    
    var refreshTrigger: Signal<Void> {
        return refreshRelay.asSignal()
    }
    
    private let refreshRelay = PublishRelay<Void>()
    
    var present: Signal<SplashRouter.Route> {
        return presentRelay.asSignal()
    }
    
    private let presentRelay = PublishRelay<SplashRouter.Route>()
    
    func inject(_ presenter: SplashPresenterProtocol) {
        self.presenter = presenter
    }

    private let disposeBag = DisposeBag()
    
    private func setUpLoadingView() {
        view.addSubview(loadingView)
        constrain(view, loadingView) { view, loadingView in
            view.edges == loadingView.edges
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        rx.sentMessage(#selector(viewWillAppear(_:)))
            .take(1)
            .map { _ in }
            .bind(to: refreshRelay)
            .disposed(by: disposeBag)
        
//        presenter.isConnectNetWork
//            .subscribeOn(ConcurrentMainScheduler.instance)
//            .filter { $0 }
//            .map { _ in }
//            .bind(to: refreshRelay)
//            .disposed(by: disposeBag)
        
        presenter.isLoading
            .observeOn(ConcurrentMainScheduler.instance)
            .map { !$0 }
            .bind(to: loadingView.rx.isHidden)
            .disposed(by: disposeBag)
        
        presenter.hasAccessToken
            .subscribeOn(MainScheduler.instance)
            .subscribe(onNext: { [weak presentRelay] isAccessToken in
                guard let presentRelay = presentRelay else { return }
                if isAccessToken {
                    presentRelay.accept(.gistList)
                } else {
                    presentRelay.accept(.login)
                }
            })
            .disposed(by: disposeBag)
        
        // network状態の確認
            // ok
                // indicator ぐるぐる
                    // UserDefaultsにログイン情報があるか確認する。
                        // ある場合 -> GistListViewContoroller
                        // ない場合 -> LoginViewController
                // indicator ストップ
            // no -> showAlert
    }
}
