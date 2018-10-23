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
        
        print(url)
        print(request.parameters)
        Alamofire.request(url, method: request.method ,parameters: request.parameters).response { result in
            
            guard let data = result.data else { fatalError("invalid json type") }
            
            print(result)
            
            
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

