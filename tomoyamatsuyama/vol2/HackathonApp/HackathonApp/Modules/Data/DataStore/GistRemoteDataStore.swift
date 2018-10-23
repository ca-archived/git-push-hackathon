import RxSwift
import RxCocoa
import Foundation
import Alamofire
import UIKit

protocol GistRemoteDataStoreProtocol {
    func fetchAllGists() -> Observable<GistList>
    func post(_ gist: GistCreateModel) -> Completable
}

final class GistRemoteDataStore: GistRemoteDataStoreProtocol {
    func post(_ gist: GistCreateModel) -> Completable {
        guard let accessToken = OAuth.accessToken.value else {
            fatalError("TODO: 再ログインさせる。")
        }
        guard let description = gist.description, let isPublic = gist.isPublic, let title = gist.title, let content = gist.content else { fatalError("TODO") }
        
        let request = GistPostRequest(method: .post, host: .api, path: "/gists", parameters: ["description": description,
                                                                                               "public": isPublic,
                                                                                               "files": [title:
                                                                                                            ["content": content]
                                                                                                        ]
                                                                                            ])
        
        return API.rx.send(request)
    }
    
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

struct GistPostRequest: APIRequestable {
    typealias Response = GistList
    var method: HTTPMethod
    var host: Host
    var path: String
    var parameters: [String : Any]
}
