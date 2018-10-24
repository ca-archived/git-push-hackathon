enum NotificationName {
    case dismissGistCreate
    
    var name: String {
        switch self {
        case .dismissGistCreate:
            return "dismissGistCreate"
        }
    }
}
