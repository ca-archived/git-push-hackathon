import RxSwift
import RxCocoa
import Foundation
import Alamofire
import UIKit

protocol GistRemoteDataStoreProtocol {
    func fetchAllGists() -> Observable<GistList>
}

final class GistRemoteDataStore: GistRemoteDataStoreProtocol {
    func fetchAllGists() -> Observable<GistList> {
        guard let accessToken = OAuth.accessToken.value else {
            fatalError("TODO: 再ログインさせる。")
        }
        let request = GistListRequest(method: .get, host: .api, path: "/gists", parameters: ["access_token" : accessToken])
        
        return API.rx.send(request)
    }
}

struct GistListRequest: APIRequestable {
    typealias Response = GistList
    var method: HTTPMethod
    var host: Host
    var path: String
    var parameters: [String : Any]
}
