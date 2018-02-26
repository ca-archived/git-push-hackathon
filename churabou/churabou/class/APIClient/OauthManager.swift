import Foundation

class OauthManager {
    
    static func getToken(code: String, completion: @escaping (String) -> Swift.Void) {
        
        let urlString = "https://github.com/login/oauth/access_token"
        
        guard let url = URL(string: urlString) else {
            return
        }

        let request = NSMutableURLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let params: [String: Any] = [
            "client_id": Config.client_id,
            "client_secret": Config.client_secret,
            "code": code
        ]

        request.httpBody = try! JSONSerialization.data(withJSONObject: params, options: .prettyPrinted)
        
        let task: URLSessionDataTask = URLSession.shared.dataTask(with: request as URLRequest, completionHandler: { (data, response, error) in
            
            guard let data = data, let result = String(data: data, encoding: .utf8) else {
                return
            }
//            print("result:\(result)")
            if result.prefix(7) == "access_" {
            let token = result.split(separator: "=")[1].split(separator: "&")[0]
                  completion(String(token))
            }
        })
        task.resume()
    }
}





