import Foundation

class ActivityEventViewModel {
    
    internal var target: ActivityEventTarget = .news
    fileprivate var currentPage = 1
    fileprivate var isFeachedAll = false
    
    //success
    var events: [Event] = [] {
        didSet {
            eventsDidSet?(events)
        }
    }
    //failed
    var error = "" {
        didSet {
            errorDidSet?(error)
        }
    }
    //loading
    var isLoading = false {
        didSet {
            isLoadingDidSet?(isLoading)
        }
    }
    
    var isLoadingDidSet:((Bool) -> Swift.Void)?
    var eventsDidSet: (([Event]) -> Swift.Void)?
    var errorDidSet: ((String) -> Swift.Void)?
    
    
    //MARK: Action
    
    func fetchEvent(completion: @escaping ()->()){

        let request = GetEventRequest(target)
//        isLoading = true
        GithubSession.send(request: request, completion: { (response) in
//            self.isLoading = false
            switch response {
            case .success(_, let events):
                if events.count < 30 { self.isFeachedAll = true}
                self.events = events                
                completion()
            case .failure(_, let message):
                self.error = message
            }
        })
    }
    
    func loadMoreEvent() {

        if isLoading || isFeachedAll || currentPage > 10 { return }
        isLoading = true
        currentPage += 1
        let request = GetEventRequest(target, page: currentPage)
        GithubSession.send(request: request, completion: { (response) in
            switch response {
            case .success(_, let events):
                if events.count < 30 { self.isFeachedAll = true }
                self.events += events
                self.isLoading = false
            case .failure(_, let message):
                self.error = message
                self.isLoading = false
            }
        })
    }
}

