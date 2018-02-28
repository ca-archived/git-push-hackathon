//
//  Api.swift
//  Hackathon
//
//  Created by Tomoya Matsuyama on 2018/02/26.
//  Copyright © 2018年 Tomoya Matsuyama. All rights reserved.
//

import Foundation
import Alamofire
import RxSwift

protocol Requestable {
    var host: String { get }
    var path: String { get }
    var method: ApiMethod { get }
    var parameter: Parameters { get }
}

enum ApiMethod {
    case get
    case post
    
    var value: HTTPMethod {
        switch self {
        case .get:
            return .get
        case .post:
            return .post
        }
    }
}

typealias Palameters = Parameters

class Api {
    private var configure: Requestable
    private var parameters: Parameters {
        return configure.parameter
    }
    
    private var method: HTTPMethod {
        return configure.method.value
    }
    
    private var baseUrl: String {
        return configure.host + configure.path
    }
    
    private init(configure: Requestable) {
        self.configure = configure
    }
    
    static func create(configure: Requestable) -> Api {
        return .init(configure: configure)
    }
    
    private func getAccessTokenFromTokenResponse<E: Decodable>(response: String) -> E? {
        let responseItems = response.split(separator: "&")
        for responseItem in responseItems {
            let responseItemDic = responseItem.split(separator: "=")
            if String(responseItemDic[0]) == "access_token" {
                let oauth = String(responseItemDic[1])
                let token: E? = Authentication.init(accessToken: oauth) as? E
                return token
            }
        }
        return nil
    }
    
    func request<E: Decodable>() -> Observable<E> {
        return .create({(observer : AnyObserver<E>) in
            Alamofire.request(self.baseUrl, parameters: self.parameters)
                .validate()
                .responseData { response in
                switch response.result {
                case .success:
                    guard let data = response.data else { return }
                    if E.self != Authentication.self {
                        do {
                            let result = try JSONDecoder().decode(E.self, from: data)
                            observer.onNext(result)
                            observer.onCompleted()
                        } catch let error {
                            observer.onError(error)
                        }
                    } else {
                        guard let dataString = String(data: data, encoding: .utf8) else { return }
                        let token: E = self.getAccessTokenFromTokenResponse(response: dataString)!
                        observer.onNext(token)
                        observer.onCompleted()
                    }
                case .failure(let error):
                    observer.onError(error)
                }
            }
            return Disposables.create {
                
            }
        })
    }
}
