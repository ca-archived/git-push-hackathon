//
//  APIClient.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/26.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation
import APIKit

final class APIClient<Request: BaseRequest> {
    
    class func sendRequest(request: Request,
                           _ completion: @escaping ((_ response: Request.Response?, _ error: GitHubAppError?) -> Void)) {
        debugPrint("request: \(request)")
        
        APIKit.Session.shared.send(request) { result in
            switch result {
            case .success(let response):
                completion(response,nil)
            case .failure(let error):
                if case APIKit.SessionTaskError.responseError(let customError as GitHubAppError) = error {
                    completion(nil,customError)
                } else if case APIKit.SessionTaskError.connectionError(let error as NSError) = error {
                    let customError = GitHubAppError(fromError: error)
                    completion(nil,customError)
                } else {
                    let customError = GitHubAppError(errorCode: .unknown)
                    completion(nil,customError)
                }
            }
        }
    }
}
