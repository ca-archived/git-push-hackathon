//参考: https://github.com/ishkawa/APIKit/blob/master/Sources/APIKit/Request.swift

import Foundation
import Alamofire

public protocol APIClientRequestType {
    associatedtype ResponseType
    
    var baseURL: String { get }
    var method: Alamofire.HTTPMethod { get }
    var path: String { get }
    var headers: [String: String] { get }
    var parameters: [String: String]? { get }
    
    func responseFromObject(_ object: Any) -> ResponseType?
}

extension APIClientRequestType {
    
    public var baseURL: String {
        return ""
    }
    
    public var method: HTTPMethod {
        return .get
    }
    
    public var path: String {
        return ""
    }
    
    public var headers: HTTPHeaders {
        return [:]
    }
    
    public var parameters: [String: String]? {
        return nil
    }
    
    public func responseFromObject(_ object: Any) -> ResponseType? {
        return nil
    }
}

