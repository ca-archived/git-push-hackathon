import UIKit

protocol RLViewDelegate: class {
    func didSelectCell(_ repository: Repository)
}

class RLView: UIView {
 
    var viewModel: RLViewModel?
    var delegate: RLViewDelegate?
    
    fileprivate lazy var tableView: UITableView = {
        let t = UITableView()
        t.delegate = self
        t.dataSource = self
        t.register(RepositoryTableViewCell.self)
        return t
    }()
    
    fileprivate func initializeView() {
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

extension RLView: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if let repository = viewModel?.repositories[indexPath.row] {
            delegate?.didSelectCell(repository)
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return RepositoryTableViewCell.defaultHeight
    }
}

extension RLView: UITableViewDataSource {
    
    
    func  tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {

        if let count = viewModel?.repositories.count {
            return count
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: RepositoryTableViewCell = tableView.dequeueReusableCell(forIndexPath: indexPath)
        let repo = viewModel?.repositories[indexPath.row]
        cell.update(repository: repo!)
        return cell
    }
}
