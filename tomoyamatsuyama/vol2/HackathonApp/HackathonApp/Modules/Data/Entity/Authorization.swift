import Foundation

struct Authorization: Codable {
    var accessToken: String
    
    enum CodingKeys: String, CodingKey {
        case accessToken = "access_token"
    }
}
