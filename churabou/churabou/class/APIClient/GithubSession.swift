import Foundation
import Alamofire
//import SwiftyJSON


public enum APIResponse<ResponseType> {
    case success(statusCode: Int, response: ResponseType)
    case failure(statusCode: Int, message: String)
}


struct GithubSession {

    static func send<T: GithubRequestType>(request: T, completion: @escaping (APIResponse<T.ResponseType>) -> Void) {
        
        let query = Alamofire.request(
            "\(request.baseURL)\(request.path)",
            method: request.method,
            parameters: request.parameters,
            headers: request.headers)
        
        debugPrint(query)
        
        query.validate(statusCode: 200...500).responseJSON { response in
            debugPrint(response)
            
            guard let statusCode = response.response?.statusCode else {
                completion(.failure(statusCode: 0, message: "データを取得できませんでした。"))
                return
            }
            
            switch response.result {
            case .success:
                
                guard let json = response.result.value else {
                    completion(.failure(statusCode: statusCode, message: "データを取得できませんでした。"))
                    return
                }
                
                guard let model = request.responseFromObject(json as Any) else {
                    completion(.failure(statusCode: statusCode, message: "データを取得できませんでした。"))
                    return
                }
                
                completion(.success(statusCode: statusCode, response: model))
                
            case .failure(let error):
                completion(.failure(statusCode: statusCode, message: error.localizedDescription))
            }
        }
    }
}


