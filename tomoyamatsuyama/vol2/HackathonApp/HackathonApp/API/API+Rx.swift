import RxSwift

extension Reactive where Base: API {
    static func send<R: APIRequestable>(_ request: R) -> Observable<R.Response> {
        return .create({(observer: AnyObserver<R.Response>) in
            API.send(to: request) { result in
                
                switch result {
                case let .success(response):
                    observer.onNext(response)
                    observer.onCompleted()
                    
                case let .failure(error):
                    observer.onError(error)
                }
            }
            return Disposables.create()
        })
    }
    
    static func send<R: APIRequestable>(_ request: R) -> Completable {
        return Completable.create(subscribe: { (observer) -> Disposable in
            API.send(to: request) { result in
                switch result {
                case .success:
                    observer(.completed)
                    
                case .failure(let error):
                    observer(.error(error))
                }
            }
            return Disposables.create()
        })
    }
}
