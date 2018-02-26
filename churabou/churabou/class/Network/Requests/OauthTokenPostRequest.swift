import Foundation
import Alamofire



struct OauthTokenPostRequest: APIClientRequestType {
    
    public typealias ResponseType = String
    
    fileprivate var code = ""
    
    init(code: String) {
        self.code = code
    }
    
    public var method: HTTPMethod {
        return .post
    }
    
    public var baseURL: String {
        return "https://github.com"
    }
    
    public var path: String {
        return "login/oauth/access_token"
    }
    
    public var headers: HTTPHeaders {
        let headers: HTTPHeaders = ["Content-Type": "application/json"]
        return headers
    }
    
    public var parameters: [String : String]? {
        
        let params: [String: String] = [
            "client_id": Config.client_id,
            "client_secret": Config.client_secret,
            "code": code
        ]
        
        return params
    }
    
    public func responseFromObject(_ object: Any) -> String? {
        
        print(object)
        print("おおおおお")
        print(String(describing: object))
//        let resultData = String(data: data!, encoding: .utf8)!
//        if resultData.prefix(7) == "access_" {
//            let token = resultData.split(separator: "=")[1].split(separator: "&")[0]
//            return String(token)
//        }
        return nil
    }
}
