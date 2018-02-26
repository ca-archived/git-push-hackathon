import Foundation
import Alamofire

struct GetRepositoryRequest: GithubRequestType {
    
    public typealias ResponseType = [Repository]
    
    public var method: HTTPMethod {
        return .get
    }

    public var path: String {
        return "/user/repos"
    }
    
    public func responseFromObject(_ object: Any) -> ResponseType? {
        return Repository.map(object)
    }
}



