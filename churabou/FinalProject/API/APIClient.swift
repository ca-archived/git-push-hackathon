//
//  GithubSession.swift
//  FinalProject
//
//  Created by ちゅーたつ on 2018/10/10.
//  Copyright © 2018年 ちゅーたつ. All rights reserved.
//

import Alamofire

public enum APIResponse<ResponseType> {
    case success(response: ResponseType)
    case failure(message: String)
}

protocol Request {
    associatedtype ResponseType
    var baseURL: String { get }
    var path: String { get }
    var method: HTTPMethod { get }
    var headers: HTTPHeaders { get }
    var parameters: Parameters { get }
    func response(from data: Data) -> ResponseType?
}

extension Request {

    var url: String {
        return baseURL + path
    }
    
//    var urlRequest: URLRequest {
//        var request = try! URLRequest(url: url, method: method, headers: headers)
//        request.cachePolicy = .reloadRevalidatingCacheData
//
//        let encoding: ParameterEncoding = method == .get ? URLEncoding.default : JSONEncoding.default
//        let encodedURLRequest = try! encoding.encode(request, with: parameters)
//
//        return encodedURLRequest
//    }
}

extension Request where ResponseType: Decodable {

    func response(from data: Data) -> ResponseType? {
        let decoder = JSONDecoder()
        decoder.dateDecodingStrategy = .iso8601
        return try? decoder.decode(ResponseType.self, from: data)
    }
}


struct APIClient {
    
    func send<T: Request>(_ request: T, completion: @escaping ((APIResponse<T.ResponseType>) -> Swift.Void))  {
        
        let encoding: ParameterEncoding = request.method == .get ? URLEncoding.default : JSONEncoding.default
        
        
        let query = Alamofire.request(request.url,
                                      method: request.method,
                                      parameters: request.parameters,
                                      encoding: encoding,
                                      headers: request.headers)

        query.responseData { response in
            
            print(response.description)
            switch response.result {
            case .success(let data):
                guard let statusCode = response.response?.statusCode,
                    case (200..<300) = statusCode else {
                     completion(.failure(message: "リクエストエラー"))
                        return
                }
//                let json = try? JSONSerialization.jsonObject(with: data, options: [])
//                print(json)
                print(statusCode)
                guard let object = request.response(from: data) else {
                    print("マッピングエラー")
                    completion(.failure(message: "マッピングエラー"))
                    return
                }
                
                completion(.success(response: object))
                break
            case .failure(let error):
                 print("failu")
                   completion(.failure(message: "ネットワークエラー"))
                break
            }
        }
    }
}

import RxSwift


enum APIError: Error {
    case request
}

extension APIClient {
    
    func response<T: Request>(from request: T) -> Observable<T.ResponseType> {
       
        return Observable.create { obserble in
            self.send(request) { result in
                switch result {
                case .success(let response):
                    obserble.onNext(response)
                    obserble.onCompleted()
                case .failure(let message):
                    obserble.onError(APIError.request)
                    break
                }
            }
            return Disposables.create { }
        }
    }
}
