import Foundation

class RLViewModel {

    fileprivate var currentPage = 1
    fileprivate var isFeachedAll = false

    //success
    var repositories: [Repository] = [] {
        didSet {
            repositoriesDidSet?(repositories)
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
    
    //MARK: Events
    var isLoadingDidSet:((Bool) -> Swift.Void)?
    var repositoriesDidSet: (([Repository]) -> Swift.Void)?
    var errorDidSet: ((String) -> Swift.Void)?
    
    
    //MARK: Action
    
    func fetchRepository(){
        self.isLoading = true
        let request = GetRepositoryRequest()
            GithubSession.send(request: request, completion: { (response) in
            self.isLoading = false
            switch response {
            case .success(_, let repositories):
                self.repositories = repositories
            case .failure(_, let message):
                print("failed \(message)")
            }
        })
    }

    func loadMoreRepository() {
        if isLoading || isFeachedAll { return }
        isLoading = true
    }
    
}
