import Foundation


enum OAuth {
    case accessToken
    
    var key: String {
        return "access_token"
    }
    
    var value: String? {
        return UserDefaults.standard.string(forKey: self.key)
    }
    
    func set(value: String) {
        UserDefaults.standard.set(value, forKey: self.key)
    }
}

extension OAuth {
    enum Authorize {
        case clientID
        case clientSecret
        case redirectURI
        case scope
        case webViewURL
        
        var key: String {
            switch self {
            case .clientID:
                return "client_id"
            case .clientSecret:
                return "client_secret"
            case .redirectURI:
                return "redirect_uri"
            case .scope:
                return "scope"
            case .webViewURL:
                return "web_view_url"
            }
        }
        
        var value: String {
            switch self {
            case .webViewURL:
                return "\(API.scheme + API.domain)/login/oauth/authorize?\(OAuth.Authorize.clientID.key)=\(OAuth.Authorize.clientID.value)&\(OAuth.Authorize.redirectURI.key)=\(OAuth.Authorize.redirectURI.value)&\(OAuth.Authorize.scope.key)=\(OAuth.Authorize.scope.value)"
            default:
                guard let filePath = Bundle.main.path(forResource: "Client", ofType:"plist" ) else {
                    fatalError("cant find path of config file")
                }
                
                guard let clientPlist = NSMutableDictionary(contentsOfFile:filePath) else {
                    fatalError("cant find config file")
                }
                return clientPlist.value(forKey: self.key) as! String
            }
        }
    }
}
