import Foundation

class AtomReader: NSObject {

    func parceEntry(_ url_text: String) -> [Entry] {
        
        guard let url = NSURL(string: url_text) else{
            return []
        }
        // インターネット上のXMLを取得し、NSXMLParserに読み込む
        guard let parser = XMLParser(contentsOf: url as URL) else{
            return []
        }
        parser.delegate = self
        parser.parse()
        return entries
    }
    
    
    private var currentEntry = Entry()
    private var entries: [Entry] = []
    private var tag: Tag = .other
    
    private var isReadTitle = false
    private var isReadIcon = false
    
    enum Tag {
        
        case entry
        case title
        case name
        case media
        case other
    }
}



extension AtomReader: XMLParserDelegate {
    
    // XML解析開始時に実行されるメソッド
    func parserDidStartDocument(_ parser: XMLParser) {
//        print("XML解析開始しました")
    }
    
    // 解析中に要素の開始タグがあったときに実行されるメソッド
    func parser(_ parser: XMLParser, didStartElement elementName: String, namespaceURI: String?, qualifiedName qName: String?, attributes attributeDict: [String : String]) {
        
        switch elementName {
        case "entry":
            currentEntry = Entry()
        case "title":
            tag = .title
            isReadTitle = false
        case "media:thumbnail":
            tag = .media
            currentEntry.icon = attributeDict["url"]!
        default:
            tag = .other
        }
    }
    
    // 開始タグと終了タグでくくられたデータがあったときに実行されるメソッド
    func parser(_ parser: XMLParser, foundCharacters string: String) {
        
        switch tag {
        case .title:
            
            if !isReadTitle {
                currentEntry.title = string
                isReadTitle = !isReadTitle
            }
        case .media:
            return
        default:
            return
        }
    }
    
    // 解析中に要素の終了タグがあったときに実行されるメソッド
    func parser(_ parser: XMLParser, didEndElement elementName: String, namespaceURI: String?, qualifiedName qName: String?) {
        
        if elementName == "entry" {
            entries.append(currentEntry)
        }
    }
    
    // XML解析終了時に実行されるメソッド
    func parserDidEndDocument(_ parser: XMLParser) {
//        print("XML解析終了しました")
    }
    
    // 解析中にエラーが発生した時に実行されるメソッド
    func parser(_ parser: XMLParser, parseErrorOccurred parseError: Error) {
//        print("エラー:" + parseError.localizedDescription)
    }
}

