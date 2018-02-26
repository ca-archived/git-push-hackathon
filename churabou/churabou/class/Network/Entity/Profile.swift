//import Foundation
//
//struct Profile {
//    
//    var name = ""
//    var iconURL = ""
//    
//    static func decode(json: JSON) -> Profile? {
//        guard let name = json["login"].string, let iconURL = json["avatar_url"].string else {
//            return nil
//        }
//        
//        return Profile(name: name, iconURL: iconURL)
//    }
//}
//
