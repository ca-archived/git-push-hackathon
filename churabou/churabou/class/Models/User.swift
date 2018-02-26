import Foundation

struct User {
    var name: String = ""
    var iconURL: String = ""
    
    static func decode(_ object: Any) -> User {
        
        guard let dic = object as? [String: Any] else {
            fatalError("unexpected type")
        }
        
        guard  let name = dic["login"] as? String,
            let iconURL = dic["avatar_url"] as? String else {
                fatalError("missing value")
        }
        
        return User(name: name, iconURL: iconURL)
    }
}
