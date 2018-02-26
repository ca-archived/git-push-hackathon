import Foundation
import Alamofire


//https://developer.github.com/v3/users/
struct GetUserRequest: GithubRequestType {
    
    public typealias ResponseType = User
    
    public var method: HTTPMethod {
        return .get
    }
    
    public var path: String {
        return "/user"
    }
    
    public func responseFromObject(_ object: Any) -> ResponseType? {
        return User.decode(object)
    }
}



