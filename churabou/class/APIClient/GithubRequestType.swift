import Foundation
import Alamofire

public protocol GithubRequestType {
    associatedtype ResponseType
    
    var baseURL: String { get }
    var method: Alamofire.HTTPMethod { get }
    var path: String { get }
    var headers: [String: String] { get }
    var parameters: [String: String]? { get }
    
    func responseFromObject(_ object: Any) -> ResponseType?
}

extension GithubRequestType {
    
    public var baseURL: String {
        return "https://api.github.com"
    }
    
    public var method: HTTPMethod {
        return .get
    }
    
    public var path: String {
        return ""
    }
    
    public var headers: HTTPHeaders {
        var header: HTTPHeaders = ["Content-type": "application/json"]
        
        if !Config.token.isEmpty {
            header["Authorization"] = "token \(Config.token)"
        }
        
        return header
    }
    
    public var parameters: [String: String]? {
        return nil
    }
    
    public func responseFromObject(_ object: Any) -> ResponseType? {
        return nil
    }
}


