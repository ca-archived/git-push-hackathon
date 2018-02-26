import UIKit

protocol ActivityEventViewDelegate: class {
    func didSelectCell(_ repository: Event)
}

class ActivityEventView: UIView {
    
    var viewModel: ActivityEventViewModel = ActivityEventViewModel()
    var delegate: ActivityEventViewDelegate?
    var reachedBottom = false {
        didSet {
            if reachedBottom == oldValue { return }
            reachedBottomDidSet?(reachedBottom)
        }
    }
    
    var reachedBottomDidSet: ((Bool)->())?
    
    fileprivate lazy var tableView: UITableView = {
        let t = UITableView()
        t.delegate = self
        t.dataSource = self
        t.register(ActivityTableViewCell.self)
        return t
    }()
    
    fileprivate var refreshControl = UIRefreshControl()

    @objc func refresh() {
        viewModel.events = []
        refreshControl.beginRefreshing()
        viewModel.fetchEvent(completion: {
            self.refreshControl.endRefreshing()
        })
    }
    
    fileprivate func initializeView() {
        refreshControl.addTarget(self, action: #selector(refresh), for: .valueChanged)
        tableView.addSubview(refreshControl)
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

extension ActivityEventView: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        delegate?.didSelectCell(viewModel.events[indexPath.row])
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return ActivityTableViewCell.defaultHeight
    }
}

extension ActivityEventView: UITableViewDataSource {
    
    
    func  tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.events.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell: ActivityTableViewCell = tableView.dequeueReusableCell(forIndexPath: indexPath)
        let event = viewModel.events[indexPath.row]
        cell.setActivity(event: event)
        return cell
    }
}

extension ActivityEventView: UIScrollViewDelegate {
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        
        let visibleHeight = scrollView.frame.height - scrollView.contentInset.top - scrollView.contentInset.bottom
        let y = scrollView.contentOffset.y + scrollView.contentInset.top
        let threshold = max(0.0, scrollView.contentSize.height - visibleHeight)
        reachedBottom = y > threshold
    }
}

