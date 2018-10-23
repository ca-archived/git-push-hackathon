import Foundation
import RxSwift
import RxCocoa
import Alamofire

protocol APIRequestable {
    associatedtype Response: Decodable
    var method: HTTPMethod { get }
    var host: Host { get }
    var path: String { get }
    var parameters: [String: Any] { get }
}

enum Host {
    case none
    case api
    
    var value: String {
        switch self {
        case .none:
            return ""
        case .api:
            return "api."
        }
    }
}

final class API: NSObject {
    static let scheme = "https://"
    static let domain = "github.com"
    
    static func send<R: APIRequestable>(to request: R, handler: @escaping ((Result<R.Response>) -> Void)) {
        
        let url = API.scheme + request.host.value + API.domain + request.path
        var headers: HTTPHeaders? = nil
        var encoding: ParameterEncoding = URLEncoding.default

        if request.path == "/gists" && request.method == .post {
            encoding = Alamofire.JSONEncoding.default
            
            if let accessToken = OAuth.accessToken.value {
                headers = ["Content-Type": "application/json",
                           "Authorization": "token \(accessToken)"]
            }
        }
        
        print(url)
        print(request.parameters)
        Alamofire.request(url, method: request.method, parameters: request.parameters,encoding: encoding, headers: headers).responseJSON { result in
            
            guard let data = result.data else { fatalError("invalid json type") }
            print(result.request?.allHTTPHeaderFields)
            print(result.request?.url)
            print(result.request?.httpBody)
            
            
            print(result.result.value)
            
            
            if R.Response.self == Authorization.self {
                guard let dataString = String(data: data, encoding: .utf8) else { return }
                if let response: R.Response = getAccessCode(from: dataString) {
                    handler(.success(response))
                } else {
                    // TODO: Error
                }
                
            } else {
                do {
                    let response: R.Response = try JSONDecoder().decode(R.Response.self, from: data)
                    handler(.success(response))
                    
                } catch let error {
                    handler(.failure(error))
                }
            }
        }
    }
}

