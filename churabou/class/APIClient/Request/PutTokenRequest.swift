import Foundation
import Alamofire

struct GetFeedRequest: GithubRequestType {
   
    typealias ResponseType = Feed
    
    public var method: HTTPMethod {
        return .get
    }
    
    public var path: String {
        
        return "/feeds"
    }
    
    public func responseFromObject(_ object: Any) -> ResponseType? {

        guard let model = ResponseType.decode(object: object) else {
            return nil
        }
        return model
    }
}


