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
        
        var key: String {
            switch self {
            case .clientID:
                return "client_id"
            case .clientSecret:
                return "client_secret"
            case .redirectURI:
                return "redirect_url"
            case .scope:
                return "scope"
            }
        }
        
        var value: String {
            
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
