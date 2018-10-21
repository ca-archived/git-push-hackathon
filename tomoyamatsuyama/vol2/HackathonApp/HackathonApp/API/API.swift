import Foundation
import RxSwift
import RxCocoa
import Alamofire

protocol APIRequestable {
    associatedtype Response: Decodable
    var method: HTTPMethod { get }
    var path: String { get }
    var parameters: [String: Any] { get }
}

final class API: NSObject {
    static let host = "https://github.com"
    
    private static func getAccessCode<T: Decodable>(from response: String) -> T? {
        let responseItems = response.split(separator: "&")
        for responseItem in responseItems {
            let responseItemDic = responseItem.split(separator: "=")
            if String(responseItemDic[0]) == "access_token" {
                let accessToken = String(responseItemDic[1])
                let result: T? = Authorization(accessToken: accessToken) as? T
                return result
            }
        }
        
        return nil
    }
    
    static func send<R: APIRequestable>(to request: R, handler: @escaping ((Result<R.Response>) -> Void)) {
        print(API.host + request.path, request.parameters)
        Alamofire.request(API.host + request.path, method: request.method ,parameters: request.parameters).responseJSON { result in
            
            guard let data = result.data else {
                fatalError("invalid json type")
            }
            
            if R.Response.self == Authorization.self {
                guard let dataString = String(data: data, encoding: .utf8) else { return }
                if let response: R.Response = getAccessCode(from: dataString) {
                    handler(.success(response))
                } else {
                    // TODO: Error
                }
            } else {
                do {
                    let response: R.Response = try JSONDecoder().decode(R.Response.self, from: data)
                    handler(.success(response))
                    
                } catch let error {
                    handler(.failure(error))
                }
            }
        }
    }
}

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
