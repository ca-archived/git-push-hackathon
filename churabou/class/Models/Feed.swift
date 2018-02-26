import Foundation

struct Feed {
    
    var timelineUrl = ""

    static func decode(object: Any) -> Feed? {
        
        guard let dic = object as? [String: Any] else {
            return nil
        }
        
        guard let timelineUrl = dic["timeline_url"] as? String else {
            return nil
        }
        
        return Feed(timelineUrl: timelineUrl)
    }
}

