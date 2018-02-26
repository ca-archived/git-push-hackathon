import Foundation
import Alamofire
//90日以内のイベント 1page30件まで pageは10まで指定できる
//https://developer.github.com/v3/activity/events/
enum ActivityEventTarget {
    case news
    case user
}

struct GetEventRequest: GithubRequestType {
    
    public typealias ResponseType = [Event]
    
    private var type: ActivityEventTarget
    private var page: Int
    init(_ type: ActivityEventTarget, page: Int = 0) {
        self.type = type
        self.page = page
    }
    public var method: HTTPMethod {
        return .get
    }
    
    var parameters: [String : String]? {
        return ["page": String(page)]
    }
    
    public var path: String {
        
        switch type {
        case .news: return "/users/\(Config.login)/received_events"
        case .user: return "/users/\(Config.login)/events"
        }
    }
    
    public func responseFromObject(_ object: Any) -> ResponseType? {
        return Event.map(object)
    }
}




