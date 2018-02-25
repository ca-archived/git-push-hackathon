//
//  GitHubAppError.swift
//  tomkenta
//
//  Created by Kenta Yokota on 2018/02/26.
//  Copyright © 2018 Kenta Yokota. All rights reserved.
//

import Foundation

enum GitHubAppErrorCode: Int {
    case unknown                        = 0
    case invalidParameter               = -1
    case invalidPassword                = -201
    case badRequest                     = 400
    case wrongToken                     = 401
    case accessForbidden                = 403
    case notFound                       = 404
    case internalServer                 = 500
    
    var errorDescription: String {
        switch self {
        case .unknown,
             .invalidParameter,
             .badRequest,
             .accessForbidden,
             .internalServer:
            return "不明なエラー \(rawValue)"
        case .invalidPassword:
            return "パスワードが正しくありません"
        case .notFound:
            return "見つかりません"
        case .wrongToken:
            return "トークンが異常です"
        }
    }
}

struct GitHubAppError: Error {
    var errorCode = GitHubAppErrorCode.unknown
    private var debugErrorMessage = "no message from server"
    var errorDescription: String {
        return errorCode.errorDescription
    }
    
    /// init from APIkit Response Error
    init(object: Any, url: URL?) {
        var code = 0
        var serverMessage = ""
        var serverCode = ""
        var remoteUrlPath = ""
        
        if let path = url?.absoluteString {
            remoteUrlPath = path
        }
        
        guard let errorInfo = object as? [String: Any] else {
            errorCode = GitHubAppErrorCode.unknown
            debugErrorMessage = "[message]: no message from server [error path]: \(remoteUrlPath)"
            debugPrint(debugErrorMessage)
            return
        }
        
        if let num = errorInfo["error_code"] as? Int {
            code = num
        }
        
        if let error = GitHubAppErrorCode(rawValue: code) {
            errorCode = error
        } else {
            errorCode = GitHubAppErrorCode.unknown
        }
        
        if let message = errorInfo["message"] as? String {
            serverMessage = message
        }
        
        if let str = errorInfo["error_code"] as? String {
            serverCode = str
        }
        
        debugErrorMessage = "[message]: \(serverMessage + ", " + serverCode) [error path]: \(remoteUrlPath)"
        debugPrint(debugErrorMessage)
    }
    
    /// init from NSError
    init(fromError error: NSError) {
        errorCode = GitHubAppErrorCode(rawValue: error.code) ?? GitHubAppErrorCode.unknown
        let errorInfo = error.userInfo as [AnyHashable: Any]
        let serverMessage = errorInfo[NSLocalizedDescriptionKey].map{ String(describing: $0) } ?? "nothing message from server"
        let remoteUrlPath = errorInfo["NSErrorFailingURLKey"].map{ String(describing: $0) } ?? ""
        debugErrorMessage = "[message]: \(serverMessage) [error path]: \(remoteUrlPath)"
        debugPrint(debugErrorMessage)
    }
    
    /// init from CustomErrorCode
    init(errorCode: GitHubAppErrorCode) {
        self.errorCode = errorCode
    }
}
