import UIKit


class ActivityFeedView: UIView {
    
    var viewModel = ActivityFeedViewModel()
    var tableView = UITableView()
    var refreshControll = UIRefreshControl()
    var reachedBottom = false {
        didSet {
            if reachedBottom == oldValue { return }
            reachedBottomDidSet?(reachedBottom)
        }
    }
    
    var reachedBottomDidSet: ((Bool) -> ())?
    
    func initializeView() {

        tableView.delegate = self
        tableView.dataSource = self
        tableView.register(ActivityTableViewCell.self)
        tableView.addSubview(refreshControll)
        addSubview(tableView)
    }
    
    
    fileprivate func setFrame() {
        tableView.frame = frame
    }
    
    func update() {
        tableView.reloadData()
    }
    
    override func draw(_ rect: CGRect) {
        setFrame()
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        initializeView()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

extension ActivityFeedView: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return ActivityTableViewCell.defaultHeight
    }
}

extension ActivityFeedView: UIScrollViewDelegate {
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        //TODO: リクエストするタイミングの調整
        let visibleHeight = scrollView.frame.height - scrollView.contentInset.top - scrollView.contentInset.bottom
        let y = scrollView.contentOffset.y + scrollView.contentInset.top
        let threshold = max(0.0, scrollView.contentSize.height - visibleHeight)
        reachedBottom = y + 10 > threshold
    }
}

extension ActivityFeedView: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.activities.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{
        
        let cell = tableView.dequeueReusableCell(forIndexPath: indexPath) as ActivityTableViewCell
        cell.setActivity(feed: viewModel.activities[indexPath.row])
        return cell
    }
}

