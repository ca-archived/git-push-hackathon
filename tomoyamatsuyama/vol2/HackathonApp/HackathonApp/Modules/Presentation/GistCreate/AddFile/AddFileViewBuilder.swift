import UIKit

struct AddFileViewBuilder {
    static func build(with viewModel: GistCreateModel) -> AddFileViewController {
        let viewController: AddFileViewController = .instantiate()
        viewController.inject(viewModel)
        
        return viewController
    }
}

