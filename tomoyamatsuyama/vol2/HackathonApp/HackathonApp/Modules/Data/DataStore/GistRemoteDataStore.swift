import RxSwift
import RxCocoa
import Foundation
import Alamofire
import UIKit

protocol GistRemoteDataStoreProtocol {
    func fetchAllGists() -> Observable<GistList>
    func post(_ gist: GistCreateModel) -> Observable<GistList>
}

final class GistRemoteDataStore: GistRemoteDataStoreProtocol {
    func post(_ gist: GistCreateModel) -> Observable<GistList> {
        guard
            let accessToken = OAuth.accessToken.value,
            let description = gist.description,
            let isPublic = gist.isPublic,
            let title = gist.title,
            let content = gist.content
        else { return .empty() }
        
        let request = GistPostRequest(method: .post,
                                      host: .api,
                                      path: "/gists",
                                      encoding: Alamofire.JSONEncoding.default,
                                      headers: ["Content-Type": "application/json", "Authorization": "token \(accessToken)"],
                                      parameters: ["description": description, "public": isPublic, "files": [title: ["content": content]]])
        
        return API.rx.send(request)
    }
    
    func fetchAllGists() -> Observable<GistList> {
        guard let accessToken = OAuth.accessToken.value else { return .empty() }
        
        let request = GistListRequest(method: .get,
                                      host: .api,
                                      path: "/gists",
                                      encoding: URLEncoding.default,
                                      headers: nil,
                                      parameters: ["access_token" : accessToken])
        
        return API.rx.send(request)
    }
}

struct GistListRequest: APIRequestable {
    typealias Response = GistList
    var method: HTTPMethod
    var host: Host
    var path: String
    var encoding: ParameterEncoding
    var headers: HTTPHeaders?
    var parameters: [String : Any]
}

struct GistPostRequest: APIRequestable {
    typealias Response = GistList
    var method: HTTPMethod
    var host: Host
    var path: String
    var encoding: ParameterEncoding
    var headers: HTTPHeaders?
    var parameters: [String : Any]
}
