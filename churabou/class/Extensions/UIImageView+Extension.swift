import UIKit

extension UIImageView {
    
    //画像を非同期で読み込む
    func loadImage(urlString: String){
        
        guard let url = URL(string: urlString) else {
            return
        }
        
        let reqest = URLRequest(url: url, cachePolicy: .returnCacheDataElseLoad, timeoutInterval: 5 * 60)
        let config =  URLSessionConfiguration.default
        let session = URLSession(configuration: config, delegate: nil, delegateQueue: OperationQueue.main)
        
        session.dataTask(with: reqest, completionHandler: { (data, response, error) in
            
            if error != nil {
                print("AsyncImageView:Error \(error?.localizedDescription)")
            } else {
                if let data = data {
                    let image = UIImage(data:data)
                    self.image = image
                }
            }
        }).resume()
    }
}
