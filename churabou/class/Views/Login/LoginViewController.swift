import UIKit


class LoginViewController: UIViewController {
    
    fileprivate var webView = UIWebView()
    
    fileprivate func initializeView() {
        title = "login"
        webView.frame = view.bounds
        webView.delegate = self
        view.addSubview(webView)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initializeView()
        loadLoginView()
    }
    
    fileprivate func loadLoginView() {
        
        let client_id = Config.client_id
        guard let url =  URL(string: "https://github.com/login/oauth/authorize?client_id=\(client_id)") else {
            return
        }
        let request = URLRequest(url: url)
        webView.loadRequest(request)
    }
    
    fileprivate func presenView() {
        
        let c = LoginUserController()
        let n = UINavigationController(rootViewController: c)
        self.present(n, animated: false, completion: nil)
    }
    
    //リダイレクトで受け取ったcodeを使ってTokenを取得する
    fileprivate func featchToken(code: String) {

        OauthManager.getToken(code: String(code), completion: { token in
            if !token.isEmpty {

                Config.token = token
                DispatchQueue.main.async {
                     self.presenView()
                }
            }
        })
    }
}


extension LoginViewController: UIWebViewDelegate {
    
    func webView(_ webView: UIWebView, shouldStartLoadWith request: URLRequest, navigationType: UIWebViewNavigationType) -> Bool {
        
        guard let url = request.url?.absoluteString else { return true }
        if !url.contains("code") { return true }
        let code = String(url.split(separator: "=")[1])
        featchToken(code: code)
        return true
    }
}






