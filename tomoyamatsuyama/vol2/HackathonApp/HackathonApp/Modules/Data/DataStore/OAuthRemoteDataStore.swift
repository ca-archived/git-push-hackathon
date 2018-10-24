import RxSwift
import RxCocoa
import Foundation
import Alamofire
import UIKit

protocol OAuthRemoteDataStoreProtocol {
    func fetchAccessToken(with code: String) -> Observable<Authorization>
}

final class OAuthRemoteDataStore: OAuthRemoteDataStoreProtocol {
    func fetchAccessToken(with code: String) -> Observable<Authorization> {
        let request = OAuthAPIRequest(method: .post,
                                      host: .none,
                                      path: "/login/oauth/access_token",
                                      encoding: URLEncoding.default,
                                      headers: nil, parameters: [OAuth.Authorize.clientID.key: OAuth.Authorize.clientID.value,OAuth.Authorize.clientSecret.key: OAuth.Authorize.clientSecret.value,
                                                                 "code": code])
        return API.rx.send(request)
    }
}

struct OAuthAPIRequest: APIRequestable {
    typealias Response = Authorization
    var method: HTTPMethod
    var host: Host
    var path: String
    var encoding: ParameterEncoding
    var headers: HTTPHeaders?
    var parameters: [String : Any]
}
