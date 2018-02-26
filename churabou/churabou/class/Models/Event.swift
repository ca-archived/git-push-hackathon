import Foundation

//https://developer.github.com/v3/activity/events/types/
private struct Repo {
    var name: String
    
    init(_ dic: [String: Any]) {
        
        guard let name = dic["name"] as? String else {
            fatalError("")
        }
        
        self.name = name
    }
}

struct Event {
    
    var type: String
    var payload: [String: Any]
    var user: User
    var repoName: String
    var createdAt: String
    
    init(_ object: Any) {
        
        guard let dic = object as? [String: Any] else {
            fatalError("辞書変換失敗")
        }
        
        guard let type = dic["type"] as? String else {
            fatalError("type missing")
        }
        
        guard let actor = dic["actor"] as? [String : Any] else {
            fatalError("actor missing")
        }
        
        guard let createdAt = dic["created_at"] as? String else {
            fatalError("createdAt missing")
        }
        
        guard let repo = dic["repo"] as? [String : Any] else {
            fatalError("repo missing")
        }
        
        guard let payload = dic["payload"] as? [String : Any] else {
            fatalError("payload missing")
        }
        
        self.type = type
        self.user = User.decode(actor)
        self.repoName = Repo(repo).name
        self.createdAt = createdAt
        self.payload = payload
    }
    
    
    static func map(_ object: Any) -> [Event] {
        
        guard let ary = object as? [Any] else {
            fatalError("辞書変換失敗")
        }
        
        return ary.map { Event($0) }
    }
    
    var displayedString: String {
        
        var str = ""
        str += user.name
        str += " \(readPayload()) \(repoName) "
        return str
    }
}





extension Event {
    
    
    func readPayload() -> String {
        
        switch type {
        case "CreateEvent":
            return "created"
            
        case "PushEvent":
            return "pushed"
            
        case "WatchEvent":
            
            
            guard let action = payload["action"] as? String else {
                fatalError("missing action")
            }
            
            return action
        default:
            return ""
        }
    }
    
    
    //    https://samoylov.eu/2016/09/19/implementing-time-since-function-in-swift-3/
    //https://qiita.com/marty-suzuki/items/e1bad9eab06575dd007b
    // https://github.com/zemirco/swift-timeago/blob/master/swift-timeago/TimeAgo.swift
    var timeago: String {
        
        let dateFormatter = DateFormatter()
//        dateFormatter.locale = Locale(identifier: "ja_JP")
//        dateFormatter.locale = Locale(identifier: "en_US_POSIX")
//        dateFormatter.timeZone = TimeZone(secondsFromGMT: 0)
        dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ssZZZZ"
        guard let date = dateFormatter.date(from: createdAt) else {
            return ""
        }
        
        let calendar = Calendar.current
        let now = Date()
        let unitFlags: NSCalendar.Unit = [.second, .minute, .hour, .day, .weekOfYear, .month, .year]
        let components = (calendar as NSCalendar).components(unitFlags, from: date, to: now, options: [])
        
        if let year = components.year, year >= 2 {
            return "\(year) years ago"
        }
        
        if let year = components.year, year >= 1 {
            return "Last year"
        }
        
        if let month = components.month, month >= 2 {
            return "\(month) months ago"
        }
        
        if let month = components.month, month >= 1 {
            return "Last month"
        }
        
        if let week = components.weekOfYear, week >= 2 {
            return "\(week) weeks ago"
        }
        
        if let week = components.weekOfYear, week >= 1 {
            return "Last week"
        }
        
        if let day = components.day, day >= 2 {
            return "\(day) days ago"
        }
        
        if let day = components.day, day >= 1 {
            return "1 day ago"
        }
        
        if let hour = components.hour, hour >= 2 {
            return "\(hour) hours ago"
        }
        
        if let hour = components.hour, hour >= 1 {
            return "An hour ago"
        }
        
        if let minute = components.minute, minute >= 2 {
            return "\(minute) minutes ago"
        }
        
        if let minute = components.minute, minute >= 1 {
            return "A minute ago"
        }
        
        if let second = components.second, second >= 3 {
            return "\(second) seconds ago"
        }
        
        return "Just now"
    }
}
