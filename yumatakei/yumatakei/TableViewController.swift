//
//  TableViewController.swift
//  yumatakei
//
//  Created by 武井　悠真 on 2018/02/24.
//  Copyright © 2018年 yuma. All rights reserved.
//

import UIKit
import SwiftyJSON
import Alamofire

class TableViewController: UITableViewController {
    
    @IBOutlet var tableview: UITableView!
    var articles:[JSON] = []
    var username:String = ""
    var url:String = ""
    
    @IBOutlet var name: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableview.delegate = self
        tableview.dataSource = self
        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
        getEvents()
        
        tableview.reloadData()
        
    }
    
    func getEvents() {
        Alamofire.request("https://api.github.com/user", method: .get, parameters: ["access_token": accesstoken])
            .responseJSON { response in
                if let json = response.result.value {
                    let result = JSON(json)
                    self.username = result["login"].stringValue
                    self.url = "https://api.github.com/users/" + self.username + "/received_events"
                    
                    Alamofire.request(self.url, method: .get, parameters: ["access_token": accesstoken])
                        .responseJSON { response in
                            if let json = response.result.value {
                                let result = JSON(json)
                                result.forEach { (_, data) in
                                    self.articles.append(data)
                                    self.tableview.reloadData()
                                }
                            }
                    }
                }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return articles.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableview.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        let article:JSON = articles[indexPath.row]
        var str:String = article["actor"]["login"].stringValue
        if article["payload"]["action"] == "started" {
            str = str + " starred " + article["repo"]["name"].stringValue
        }else if article["payload"]["action"] == "added" {
            str = str + " started followimng " + article["repo"]["name"].stringValue
        }else if article["payload"]["forkee"] != nil {
            str = str + " forked " + article["repo"]["name"].stringValue
        }else if article["payload"]["action"] == nil {
            str = str + " created a repoitory " + article["repo"]["name"].stringValue
        }
        cell.textLabel?.text = str

        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let article:JSON = articles[indexPath.row]
        let url = URL(string: "https://github.com/" + article["repo"]["name"].stringValue)
        print(article)
        print(indexPath)
        if UIApplication.shared.canOpenURL(url!) {
            UIApplication.shared.open(url!)
        }
    }

    

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
