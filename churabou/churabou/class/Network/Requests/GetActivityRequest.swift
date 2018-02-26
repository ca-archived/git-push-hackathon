import Foundation
import Alamofire

struct GetActivityRequest: APIClientRequestType {
    
    public typealias ResponseType = Feed
    
    public var method: HTTPMethod {
        return .get
    }
    
    public var baseURL: String {
        return "https://api.github.com"
    }
    
    public var path: String {
        return "/feeds"
    }
    
    public var headers: HTTPHeaders {
        let headers: HTTPHeaders = ["Authorization": "token \(Config.token)"]
        return headers
    }
    
    public func responseFromObject(_ object: Any) -> Feed? {
        let json = JSON(object)
        guard let model = ResponseType.decode(json: json) else {
            return nil
        }
        return model
    }
}



