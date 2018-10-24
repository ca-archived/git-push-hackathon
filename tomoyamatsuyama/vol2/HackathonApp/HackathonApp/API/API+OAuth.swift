import RxSwift

extension API {
    static func getAccessCode<T: Decodable>(from response: String) -> T? {
        let responseItems = response.split(separator: "&")
        for responseItem in responseItems {
            let responseItemDic = responseItem.split(separator: "=")
            if String(responseItemDic[0]) == "access_token" {
                let accessToken = String(responseItemDic[1])
                let result: T? = Authorization(accessToken: accessToken) as? T
                return result
            }
        }
        return nil
    }
}
